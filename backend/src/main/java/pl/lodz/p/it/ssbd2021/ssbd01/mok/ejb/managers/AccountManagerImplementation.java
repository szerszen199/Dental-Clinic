package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

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
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.HashGenerator;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.MailProvider;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.RandomPasswordGenerator;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.ServletContext;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Typ Account manager implementation.
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class AccountManagerImplementation extends AbstractManager implements AccountManager {

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private LoggedInAccountUtil loggedInAccountUtil;

    @Inject
    private AccessLevelFacade accessLevelFacade;

    @Inject
    private HashGenerator hashGenerator;

    @Inject
    private RandomPasswordGenerator passwordGenerator;

    @EJB
    private JwtEmailConfirmationUtils jwtEmailConfirmationUtils;

    @Inject
    private MailProvider mailProvider;
    private Account account;

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

        mailProvider.sendActivationMail(
                account.getEmail(),
                servletContext.getContextPath(),
                jwtEmailConfirmationUtils.generateRegistrationConfirmationJwtTokenForUser(account.getLogin())
        );
    }

    @Override
    public void removeAccount(Long id) throws AppBaseException {
        Account account = accountFacade.find(id);
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
            String login = jwtEmailConfirmationUtils.getUserNameFromJwtToken(jwt);
            accountFacade.findByLogin(login).setEnabled(true);
            mailProvider.sendActivationConfirmationMail(accountFacade.findByLogin(login).getEmail());
        } catch (AppBaseException | ParseException e) {
            throw AccountException.noSuchAccount(e);
        }
    }

    @Override
    public void lockAccount(String login) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setActive(false);
        // TODO: Zastanowić się i ustawić pole modifiedBy po zablokowaniu konta przez system po nieudanych logowaniach
        //account.setModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
    }

    @Override
    public void unlockAccount(String login) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setActive(true);
        accountFacade.edit(account);
    }

    @Override
    public void editAccount(Account account) throws AppBaseException {
        account.setModifiedBy(account);
        Account old = accountFacade.findByLogin(account.getLogin());
        //        if (old.getActive() != account.getActive() || old.getEnabled() != account.getEnabled() || !old.getPesel().equals(account.getPesel())) {
        //            throw DataValidationException.accountEditValidationError();
        //        }
        if (account.getFirstName() != null) {
            old.setFirstName(account.getFirstName());
        }
        if (account.getLastName() != null) {
            old.setLastName(account.getLastName());
        }
        if (account.getEmail() != null) {
            old.setEmail(account.getEmail());
        }
        if (account.getPhoneNumber() != null) {
            old.setPhoneNumber(account.getPhoneNumber());
        }
        if (account.getPesel() != null) {
            old.setPesel(account.getPesel());
        }
        accountFacade.edit(old);
    }

    @Override
    public void editOtherAccount(Account account) throws AppBaseException {
        account.setModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
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
    public void changePassword(String login, String oldPassword, String newPassword) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
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
        account.setPassword(generateNewRandomPassword());
        // TODO: send mail with new password
    }

    @Override
    public void resetPassword(String login) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
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
    public void setDarkMode(String login, boolean isDarkMode) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setDarkMode(isDarkMode);
        accountFacade.edit(account);
    }

    @Override
    public void setLanguage(String login, String language) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setLanguage(language);
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
}
