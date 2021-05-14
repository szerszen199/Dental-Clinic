package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AdminData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.ReceptionistData;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.DataValidationException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordsNotMatchException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordsSameException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtEmailConfirmationUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.HashGenerator;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.MailProvider;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.RandomPasswordGenerator;


/**
 * Typ Account manager implementation.
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class AccountManagerImplementation implements AccountManager {

    private static final String DEFAULT_URL = "http://studapp.it.p.lodz.pl:8001";

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private AccessLevelFacade accessLevelFacade;

    @Context
    private SecurityContext securityContext;

    @Inject
    private HashGenerator hashGenerator;

    @Inject
    private RandomPasswordGenerator passwordGenerator;

    @Inject
    private MailProvider mailProvider;

    @EJB
    private JwtEmailConfirmationUtils jwtEmailConfirmationUtils;

    @Override
    public void createAccount(Account account, ServletContext servletContext) throws AppBaseException {
        account.setPassword(hashGenerator.generateHash(account.getPassword()));

        AccessLevel patientData = new PatientData();
        patientData.setActive(true);
        patientData.setCreatedBy(account);
        patientData.setAccountId(account);
        account.getAccessLevels().add(patientData);

        AccessLevel receptionistData = new ReceptionistData();
        receptionistData.setActive(false);
        receptionistData.setCreatedBy(account);
        receptionistData.setAccountId(account);
        account.getAccessLevels().add(receptionistData);

        AccessLevel doctorData = new DoctorData();
        doctorData.setActive(false);
        doctorData.setCreatedBy(account);
        doctorData.setAccountId(account);
        account.getAccessLevels().add(doctorData);

        AccessLevel adminData = new AdminData();
        adminData.setActive(false);
        adminData.setCreatedBy(account);
        adminData.setAccountId(account);
        account.getAccessLevels().add(adminData);

        account.setCreatedBy(account);
        accountFacade.create(account);

        this.sendConfirmationLink(
                account.getEmail(),
                buildConfirmationLink(account.getLogin(), servletContext.getContextPath())
        );
    }

    @Override
    public void removeAccount(Account account) {
        accountFacade.remove(account);
    }

    @Override
    public void confirmAccount(Long id) throws AppBaseException {
        accountFacade.find(id).setEnabled(true);
    }

    @Override
    public void confirmAccountByToken(String jwt) throws AppBaseException {
        if (!jwtEmailConfirmationUtils.validateRegistrationConfirmationJwtToken(jwt)) {
            throw AccountException.invalidConfirmationToken();
        }
        try {
            String login = jwtEmailConfirmationUtils.getUserNameFromRegistrationConfirmationJwtToken(jwt);
            accountFacade.findByLogin(login).setEnabled(true);
        } catch (AppBaseException | ParseException e) {
            throw AccountException.noSuchAccount(e);
        }
    }

    @Override
    public Account getLoggedInAccount() throws AppBaseException {
        if (securityContext.getCallerPrincipal() == null) {
            return null;
        } else {
            return accountFacade.findByLogin(securityContext.getCallerPrincipal().getName());
        }
    }

    @Override
    public void lockAccount(Long id) throws AppBaseException {
        Account account = accountFacade.find(id);
        account.setActive(false);
    }

    @Override
    public void unlockAccount(Long id) throws AppBaseException {
        Account account = accountFacade.find(id);
        account.setActive(true);
        accountFacade.edit(account);
    }

    @Override
    public void editAccount(Account account) throws AppBaseException {
        account.setModifiedBy(account);
        Account old = accountFacade.findByLogin(account.getLogin());
        if (old.getActive() != account.getActive() || old.getEnabled() != account.getEnabled() || !old.getPesel().equals(account.getPesel())) {
            throw DataValidationException.accountEditValidationError();
        }
        accountFacade.edit(account);
    }

    @Override
    public void editOtherAccount(Account account) throws AppBaseException {
        account.setModifiedBy(getLoggedInAccount());
        Account old = accountFacade.findByLogin(account.getLogin());
        if (old.getActive() != account.getActive() || old.getEnabled() != account.getEnabled() || !old.getPesel().equals(account.getPesel())) {
            throw DataValidationException.accountEditValidationError();
        }
        accountFacade.edit(account);
    }

    @Override
    public List<Account> getAllAccounts() throws AppBaseException {
        return accountFacade.findAll();
    }

    @Override
    public void changePassword(Account account, String oldPassword, String newPassword) throws AppBaseException {
        this.verifyOldPassword(account.getPassword(), oldPassword);
        this.validateNewPassword(account.getPassword(), newPassword);
        account.setPassword(hashGenerator.generateHash(newPassword));
        accountFacade.edit(account);
    }

    @Override
    public Account findByLogin(String login) throws AppBaseException {
        return accountFacade.findByLogin(login);
    }

    @Override
    public List<Account> findByEnabled(boolean enabled) {
        return accountFacade.findByEnabled(enabled);
    }

    @Override
    public void resetPassword(Long id) throws AppBaseException {
        Account account = accountFacade.find(id);
        String generatedPassword = passwordGenerator.generate(8);
        String newPasswordHash = hashGenerator.generateHash(generatedPassword);

        account.setPassword(newPasswordHash);
        account.setPassword(generateNewRandomPassword());
        // TODO: send mail with new password
    }

    @Override
    public void resetPassword(Account account) {
        account.setPassword(generateNewRandomPassword());
        // TODO: send mail with new password
    }

    private String generateNewRandomPassword() {
        String generatedPassword = passwordGenerator.generate(8);
        return hashGenerator.generateHash(generatedPassword);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void setLastSuccessfulLoginIp(Account account, String ip) throws AppBaseException {
        account.setLastSuccessfulLoginIp(ip);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void setLastSuccessfulLoginTime(Account account, LocalDateTime time) throws AppBaseException {
        account.setLastSuccessfulLogin(time);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void increaseInvalidLoginCount(Account account) throws AppBaseException {
        account.setUnsuccessfulLoginCounter(account.getUnsuccessfulLoginCounter() + 1);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void zeroInvalidLoginCount(Account account) throws AppBaseException {
        account.setUnsuccessfulLoginCounter(0);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void setLastUnsuccessfulLoginTime(Account account, LocalDateTime time) throws AppBaseException {
        account.setLastUnsuccessfulLogin(time);
    }

    @Override
    public void updateAfterSuccessfulLogin(String login, String ip, LocalDateTime time) throws AppBaseException {
        Account account = findByLogin(login);
        setLastSuccessfulLoginIp(account, ip);
        setLastSuccessfulLoginTime(account, time);
        zeroInvalidLoginCount(account);
        //        accountFacade.edit(account);
    }

    @Override
    public void updateAfterUnsuccessfulLogin(String login, String ip, LocalDateTime time) throws AppBaseException {
        Account account = findByLogin(login);
        setLastUnsuccessfulLoginIp(account, ip);
        setLastUnsuccessfulLoginTime(account, time);
        increaseInvalidLoginCount(account);
        //        accountFacade.edit(account);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void setLastUnsuccessfulLoginIp(Account account, String ip) throws AppBaseException {
        account.setLastUnsuccessfulLoginIp(ip);
    }

    @Override
    public void setDarkMode(Account account, boolean isDarkMode) throws AppBaseException {
        account.setDarkMode(isDarkMode);
        accountFacade.edit(account);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void verifyOldPassword(String currentPasswordHash, String oldPassword) throws AppBaseException {
        if (!currentPasswordHash.contentEquals(hashGenerator.generateHash(oldPassword))) {
            throw PasswordsNotMatchException.currentPasswordNotMatch();
        }
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void validateNewPassword(String currentPasswordHash, String newPassword) throws AppBaseException {
        if (currentPasswordHash.contentEquals(hashGenerator.generateHash(newPassword))) {
            throw PasswordsSameException.passwordsNotDifferent();
        }
    }

    private void sendConfirmationLink(String email, String link) throws AppBaseException {
        mailProvider.sendActivationMail(email, link);
    }

    private String buildConfirmationLink(String login, String defaultContext) {
        StringBuilder sb = new StringBuilder(DEFAULT_URL);

        sb.append(defaultContext);
        sb.append("/api/account/confirm?token=");
        sb.append(jwtEmailConfirmationUtils.generateRegistrationConfirmationJwtTokenForUser(login));

        return sb.toString();
    }
}
