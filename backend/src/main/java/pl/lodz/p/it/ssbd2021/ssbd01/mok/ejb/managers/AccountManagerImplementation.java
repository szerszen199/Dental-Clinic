package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AdminData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.ReceptionistData;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.EditAnotherAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.EditOwnAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JWTRegistrationConfirmationUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtEmailConfirmationUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtResetPasswordConfirmation;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.HashGenerator;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.IpAddressUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.MailProvider;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.RandomPasswordGenerator;

import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Typ Account manager implementation.
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class AccountManagerImplementation extends AbstractManager implements AccountManager, SessionSynchronization {

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

    @Inject
    private JwtEmailConfirmationUtils jwtEmailConfirmationUtils;

    @Inject
    private HttpServletRequest request;

    @Inject
    private JWTRegistrationConfirmationUtils jwtRegistrationConfirmationUtils;

    @Inject
    private MailProvider mailProvider;

    @Inject
    private JwtResetPasswordConfirmation jwtResetPasswordConfirmation;
    @Inject
    private PropertiesLoader propertiesLoader;

    @Override
    public void createAccount(Account account) throws AccountException, MailSendingException {
        String requestIp = IpAddressUtils.getClientIpAddressFromHttpServletRequest(request);
        account.setPassword(hashGenerator.generateHash(account.getPassword()));
        account.setCreatedByIp(requestIp);

        // TODO: 21.05.2021  Przetestować czy dzialaja dobrze constructory przed wstawieniem
        AccessLevel patientData = new PatientData(account, true);
        patientData.setCreatedBy(account);
        account.getAccessLevels().add(patientData);

        AccessLevel receptionistData = new ReceptionistData(account, false);
        receptionistData.setCreatedBy(account);
        account.getAccessLevels().add(receptionistData);

        AccessLevel doctorData = new DoctorData(account, false);
        doctorData.setCreatedBy(account);
        account.getAccessLevels().add(doctorData);

        AccessLevel adminData = new AdminData(account, false);
        adminData.setCreatedBy(account);
        account.getAccessLevels().add(adminData);

        try {
            accountFacade.findByLoginOrEmailOrPesel(account.getLogin(), account.getEmail(), account.getPesel());
        } catch (AccountException accountException) {
            account.setCreatedBy(account);
            try {
                accountFacade.create(account);
            } catch (Exception e) {
                throw AccountException.accountCreationFailed();
            }
            try {
                mailProvider.sendActivationMail(
                        account.getEmail(),
                        jwtRegistrationConfirmationUtils.generateJwtTokenForUsername(account.getLogin())
                );
            } catch (Exception e) {
                throw MailSendingException.activationLink();
            }
            return;
        } catch (AppBaseException e) {
            throw AccountException.accountCreationFailed();
        }
        throw AccountException.accountLoginEmailPeselExists();
    }

    @Override
    public void createAccountByAdministrator(Account account) throws AppBaseException {
        account.setPassword(hashGenerator.generateHash(passwordGenerator.generate(32)));
        AccessLevel patientData = new PatientData(account, true);
        patientData.setCreatedBy(account);
        account.getAccessLevels().add(patientData);

        AccessLevel receptionistData = new ReceptionistData(account, false);
        receptionistData.setCreatedBy(account);
        account.getAccessLevels().add(receptionistData);

        AccessLevel doctorData = new DoctorData(account, false);
        doctorData.setCreatedBy(account);
        account.getAccessLevels().add(doctorData);

        AccessLevel adminData = new AdminData(account, false);
        adminData.setCreatedBy(account);
        account.getAccessLevels().add(adminData);

        account.setCreatedBy(account);
        accountFacade.create(account);

        this.resetPassword(account.getLogin(), loggedInAccountUtil.getLoggedInAccountLogin());
    }

    @Override
    public void removeAccount(Long id) throws AppBaseException {
        Account account = accountFacade.find(id);
        accountFacade.remove(account);
    }

    @Override
    public void setEmailRecallTrue(String login) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setEmailRecall(true);
        accountFacade.edit(account);
    }

    @Override
    public void confirmAccountByToken(String jwt) throws AccountException, MailSendingException {
        if (!jwtEmailConfirmationUtils.validateJwtToken(jwt)) {
            throw AccountException.invalidConfirmationToken();
        }
        String login;
        Account account;
        try {
            login = jwtEmailConfirmationUtils.getUserNameFromJwtToken(jwt);
        } catch (Exception e) {
            throw AccountException.invalidConfirmationToken();
        }
        try {
            account = accountFacade.findByLogin(login);
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
        account.setEnabled(true);
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw AccountException.accountConfirmationByTokenFailed();
        }
        try {
            mailProvider.sendActivationConfirmationMail(accountFacade.findByLogin(login).getEmail());
        } catch (Exception e) {
            throw MailSendingException.activationConfirmation();
        }

    }

    @Override
    public void resetPasswordByToken(String jwt) throws AccountException, MailSendingException, PasswordException {
        if (!jwtResetPasswordConfirmation.validateJwtToken(jwt)) {
            throw AccountException.invalidConfirmationToken();
        }
        try {
            String input = jwtResetPasswordConfirmation.getUserNameFromJwtToken(jwt);
            this.resetPassword(input);
        } catch (ParseException e) {
            throw AccountException.noSuchAccount(e);
        } catch (MailSendingException e) {
            throw MailSendingException.passwordResetMail();
        }
    }

    @Override
    public void lockAccount(String login) throws AppBaseException {
        Account account;
        try {
            account = accountFacade.findByLogin(login);
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
        account.setActive(false);
        account.setLastBlockUnlockDateTime(LocalDateTime.now());
        account.setLastBlockUnlockIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        if (!loggedInAccountUtil.getLoggedInAccountLogin().equals(propertiesLoader.getAnonymousUserName())) {
            // TODO: Zastanowić się i ustawić pole modifiedBy po zablokowaniu konta przez system po nieudanych logowaniach
            account.setLastBlockUnlockModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        }
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw AccountException.accountLockFailed();
        }
        // TODO: Zastanowić się i ustawić pole modifiedBy po zablokowaniu konta przez system po nieudanych logowaniach
        //account.setModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
    }

    @Override
    public void unlockAccount(String login) throws AccountException {
        Account account;
        try {
            account = accountFacade.findByLogin(login);
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
        account.setLastBlockUnlockDateTime(LocalDateTime.now());
        account.setLastBlockUnlockIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        account.setLastBlockUnlockModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        account.setActive(true);
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw AccountException.accountUnlockFailed();
        }
    }

    @Override
    public void editOwnAccount(EditOwnAccountRequestDTO editOwnAccountRequestDTO, ServletContext servletContext) throws MailSendingException, AccountException {
        Account toBeModified;
        try {
            toBeModified = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (AppBaseException e) {
            throw AccountException.accountEditFailed();
        }
        this.commonEditAccount(editOwnAccountRequestDTO, servletContext, toBeModified);
    }

    @Override
    public void editOtherAccount(EditAnotherAccountRequestDTO editAnotherAccountRequestDTO, ServletContext servletContext) throws AccountException, MailSendingException {
        Account toBeModified;
        try {
            toBeModified = accountFacade.findByLogin(editAnotherAccountRequestDTO.getLogin());
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (AppBaseException e) {
            throw AccountException.accountEditFailed();
        }
        this.commonEditAccount(editAnotherAccountRequestDTO, servletContext, toBeModified);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void commonEditAccount(EditOwnAccountRequestDTO editAccountRequestDTO, ServletContext servletContext, Account account) throws MailSendingException, AccountException {
        if (editAccountRequestDTO.getFirstName() != null) {
            account.setFirstName(editAccountRequestDTO.getFirstName());
        }
        if (editAccountRequestDTO.getLastName() != null) {
            account.setLastName(editAccountRequestDTO.getLastName());
        }
        if (editAccountRequestDTO.getEmail() != null && !account.getEmail().equals(editAccountRequestDTO.getEmail())) {
            // TODO: 21.05.2021 Wysylac maila dopieo kiedy edit się powiódł?
            try {
                mailProvider.sendEmailChangeConfirmationMail(
                        editAccountRequestDTO.getEmail(),
                        jwtEmailConfirmationUtils.generateEmailChangeConfirmationJwtTokenForUser(
                                loggedInAccountUtil.getLoggedInAccountLogin(), editAccountRequestDTO.getEmail())
                );
            } catch (MailSendingException mailSendingException) {
                throw MailSendingException.editAccountMail();
            }
        }
        if (editAccountRequestDTO.getPhoneNumber() != null) {
            account.setPhoneNumber(editAccountRequestDTO.getPhoneNumber());
        }
        if (editAccountRequestDTO.getPesel() != null) {
            account.setPesel(editAccountRequestDTO.getPesel());
        }
        try {
            account.setModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (Exception e) {
            throw AccountException.accountEditFailed();
        }
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw AccountException.accountEditFailed();
        }
    }

    @Override
    public void confirmMailChangeByToken(String jwt) throws AccountException {
        if (!jwtEmailConfirmationUtils.validateJwtToken(jwt)) {
            throw AccountException.invalidConfirmationToken();
        }
        String login;
        String newEmail;
        try {
            login = jwtEmailConfirmationUtils.getUsernameFromToken(jwt);
            newEmail = jwtEmailConfirmationUtils.getEmailFromToken(jwt);
        } catch (ParseException e) {
            throw AccountException.invalidConfirmationToken();
        }
        Account account;
        try {
            account = accountFacade.findByLogin(login);
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (Exception e) {
            throw AccountException.emailConfirmationFailed();
        }
        account.setModifiedBy(account);
        account.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        account.setEmail(newEmail);
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw AccountException.emailConfirmationFailed();
        }
    }

    @Override
    public List<Account> getAllAccounts() throws AppBaseException {
        try {
            return accountFacade.findAll();
        } catch (AppBaseException e) {
            throw AccountException.getAllAccountsFailed();
        }
    }

    @Override
    public void changePassword(String login, String oldPassword, String newPassword) throws AppBaseException {
        Account account;
        try {
            account = accountFacade.findByLogin(login);
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (AppBaseException e) {
            throw PasswordException.passwordChangeFailed();
        }
        account.setModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        account.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        if (!account.getPassword().contentEquals(hashGenerator.generateHash(oldPassword))) {
            throw PasswordException.currentPasswordNotMatch();
        }
        if (account.getPassword().contentEquals(hashGenerator.generateHash(newPassword))) {
            throw PasswordException.passwordsNotDifferent();
        }
        account.setPassword(hashGenerator.generateHash(newPassword));
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw PasswordException.passwordChangeFailed();
        }
    }

    @Override
    public Account findByLogin(String login) throws AppBaseException {
        return accountFacade.findByLogin(login);
    }

    @Override
    public List<Account> findByEnabled(boolean enabled) throws AppBaseException {
        return accountFacade.findByEnabled(enabled);
    }

    @Override
    public void resetPassword(String login) throws AccountException, MailSendingException, PasswordException {
        Account account;
        try {
            account = accountFacade.findByLogin(login);
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
        // TODO: 21.05.2021 Dlugosc do zmiennej w pliku konfiguracyjnym
        account.setPassword(hashGenerator.generateHash(passwordGenerator.generate(32)));
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw PasswordException.passwordResetFailed();
        }
        String pass = passwordGenerator.generate(32);
        account.setPassword(hashGenerator.generateHash(pass));
        mailProvider.sendGeneratedPasswordMail(account.getEmail(), pass);
        // TODO: send mail with new password
    }

    @Override
    public void sendResetPasswordConfirmationEmail(String login) throws AppBaseException {
        Account account;
        try {
            account = accountFacade.findByLogin(login);
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
        try {
            mailProvider.sendResetPassConfirmationMail(
                    account.getEmail(),
                    jwtResetPasswordConfirmation.generateJwtTokenForUsername(
                            login)
            );
        } catch (MailSendingException mailSendingException) {
            throw MailSendingException.editAccountMail();
        }
        // TODO: send mail with new password
    }

    @Override
    public void updateAfterSuccessfulLogin(String login, String ip, LocalDateTime time) throws AppBaseException {
        Account account;
        try {
            account = findByLogin(login);
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (AppBaseException e) {
            throw AccountException.accountEditFailed();
        }
        account.setLastSuccessfulLoginIp(ip);
        account.setLastSuccessfulLogin(time);
        account.setUnsuccessfulLoginCounter(0);
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw AccountException.updateAfterSuccessfulLogin();
        }
    }

    @Override
    public void updateAfterUnsuccessfulLogin(String login, String ip, LocalDateTime time) throws AppBaseException {
        Account account;
        try {
            account = findByLogin(login);
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (AppBaseException e) {
            throw AccountException.accountEditFailed();
        }
        account.setLastUnsuccessfulLoginIp(ip);
        account.setLastUnsuccessfulLogin(time);
        account.setUnsuccessfulLoginCounter(account.getUnsuccessfulLoginCounter() + 1);
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw AccountException.updateAfterUnsuccessfulLogin();
        }
    }

    @Override
    public void setDarkMode(String login, boolean isDarkMode) throws AppBaseException {
        Account account;
        try {
            account = accountFacade.findByLogin(login);
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (AppBaseException e) {
            throw AccountException.accountEditFailed();
        }
        account.setModifiedBy(account);
        account.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        account.setDarkMode(isDarkMode);
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw AccountException.accountSetDarkMode();
        }

    }

    @Override
    public void setLanguage(String login, String language) throws AppBaseException {
        Account account;
        try {
            account = accountFacade.findByLogin(login);
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (AppBaseException e) {
            throw AccountException.accountEditFailed();
        }
        account.setModifiedBy(account);
        account.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        account.setLanguage(language);
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw AccountException.accountSetLanguage();
        }
    }

}
