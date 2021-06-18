package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AdminData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.ReceptionistData;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.MedicalDocumentationException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.MedicalDocumentationFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.common.ChangePasswordDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.common.SetNewPasswordDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.EditAnotherAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.EditOwnAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JWTRegistrationConfirmationUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtEmailConfirmationUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtResetPasswordConfirmation;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtUnlockByMailConfirmationUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.HashGenerator;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.IpAddressUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.MailProvider;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.RandomPasswordGenerator;


/**
 * Implementacja menadżera konta.
 */
@Stateful
@PermitAll
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class AccountManagerImplementation extends AbstractManager implements AccountManager, SessionSynchronization {

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private MedicalDocumentationFacade medicalDocumentationFacade;

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
    private JwtUnlockByMailConfirmationUtils jwtUnlockByMailConfirmationUtils;

    @Inject
    private MailProvider mailProvider;

    @Inject
    private JwtResetPasswordConfirmation jwtResetPasswordConfirmation;
    @Inject
    private PropertiesLoader propertiesLoader;

    @Override
    public void createAccount(Account account) throws AccountException, MailSendingException, MedicalDocumentationException {
        String requestIp = IpAddressUtils.getClientIpAddressFromHttpServletRequest(request);
        account.setPassword(hashGenerator.generateHash(account.getPassword()));
        account.setCreatedByIp(requestIp);

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
                        jwtRegistrationConfirmationUtils.generateJwtTokenForUsername(account.getLogin()), account.getLanguage()
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
    public void removeAccount(Long id) throws AppBaseException {
        Account account = accountFacade.find(id);
        accountFacade.remove(account);
    }

    @Override
    public void setActiveFalse(String login) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setActive(false);
        accountFacade.edit(account);
    }

    @Override
    public void setEmailRecallTrue(String login) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setEmailRecall(true);
        accountFacade.edit(account);
    }


    @Override
    public void confirmAccountByToken(String jwt) throws AccountException, MailSendingException {
        if (!jwtRegistrationConfirmationUtils.validateJwtToken(jwt)) {
            throw AccountException.invalidConfirmationToken();
        }
        String login;
        Account account;
        try {
            login = jwtRegistrationConfirmationUtils.getUserNameFromJwtToken(jwt);
        } catch (Exception e) {
            throw AccountException.invalidConfirmationToken();
        }
        try {
            account = accountFacade.findByLogin(login);
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
        if (account.getEnabled()) {
            throw AccountException.accountAlreadyConfirmed();
        }
        account.setEnabled(true);
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw AccountException.accountConfirmationByTokenFailed();
        }
        try {
            mailProvider.sendActivationConfirmationMail(account.getEmail(), account.getLanguage());
        } catch (Exception e) {
            throw MailSendingException.activationConfirmation();
        }
    }

    @Override
    public void resetPasswordByToken(String jwt) throws AccountException, MailSendingException, PasswordException {
        Account account;
        if (!jwtResetPasswordConfirmation.validateJwtToken(jwt)) {
            throw AccountException.invalidConfirmationToken();
        }
        try {
            String input = jwtResetPasswordConfirmation.getVersionAndNameFromJwtToken(jwt);
            String name = input.split("/")[0];
            String version = input.split("/")[1];
            try {
                account = accountFacade.findByLogin(name);
            } catch (AppBaseException e) {
                throw AccountException.noSuchAccount(e);
            }
            if (!String.valueOf(account.getVersion()).equals(version)) {
                throw AccountException.passwordAlreadyChanged();
            }
            this.resetPassword(name, name);
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
            account.setLastBlockUnlockModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        }
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw AccountException.accountLockFailed();
        }
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
        account.setUnsuccessfulLoginCounter(0);
        account.setActive(true);
        try {
            account.setLastBlockUnlockModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (AppBaseException e) {
            throw AccountException.accountEditFailed();
        }
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw AccountException.accountUnlockFailed();
        }
    }

    @Override
    public void editOwnAccount(EditOwnAccountRequestDTO editOwnAccountRequestDTO) throws MailSendingException, AccountException {
        Account toBeModified;
        try {
            toBeModified = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (AppBaseException e) {
            throw AccountException.accountEditFailed();
        }
        this.commonEditAccount(editOwnAccountRequestDTO, toBeModified);
    }

    @Override
    public void editOtherAccount(EditAnotherAccountRequestDTO editAnotherAccountRequestDTO) throws AccountException, MailSendingException {
        Account toBeModified;
        try {
            toBeModified = accountFacade.findByLogin(editAnotherAccountRequestDTO.getLogin());
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (AppBaseException e) {
            throw AccountException.accountEditFailed();
        }
        this.commonEditAccount(editAnotherAccountRequestDTO, toBeModified);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void commonEditAccount(EditOwnAccountRequestDTO editAccountRequestDTO, Account account) throws AccountException, MailSendingException {
        if (!editAccountRequestDTO.getVersion().equals(account.getVersion())) {
            throw AccountException.versionMismatchException();
        }
        if (editAccountRequestDTO.getFirstName() != null) {
            account.setFirstName(editAccountRequestDTO.getFirstName());
        }
        if (editAccountRequestDTO.getLastName() != null) {
            account.setLastName(editAccountRequestDTO.getLastName());
        }
        if (editAccountRequestDTO.getEmail() != null && !account.getEmail().equals(editAccountRequestDTO.getEmail())) {
            try {
                mailProvider.sendEmailChangeConfirmationMail(
                        editAccountRequestDTO.getEmail(),
                        jwtEmailConfirmationUtils.generateEmailChangeConfirmationJwtTokenForUser(
                                loggedInAccountUtil.getLoggedInAccountLogin(), editAccountRequestDTO.getEmail(), account.getLogin()),
                        account.getLanguage()
                );
            } catch (MailSendingException mailSendingException) {
                throw MailSendingException.editAccountMail();
            }
        }
        account.setPhoneNumber(editAccountRequestDTO.getPhoneNumber());
        account.setPesel(editAccountRequestDTO.getPesel());
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
        String userChangedLogin;
        try {
            login = jwtEmailConfirmationUtils.getUsernameFromToken(jwt);
            newEmail = jwtEmailConfirmationUtils.getEmailFromToken(jwt);
            userChangedLogin = jwtEmailConfirmationUtils.getChangedUserLogin(jwt);
        } catch (ParseException e) {
            throw AccountException.invalidConfirmationToken();
        }
        Account account;
        Account accountChanged;
        try {
            account = accountFacade.findByLogin(login);
            accountChanged = accountFacade.findByLogin(userChangedLogin);
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (Exception e) {
            throw AccountException.emailConfirmationFailed();
        }
        if (account.getEmail().equals(newEmail)) {
            throw AccountException.emailAlreadyChanged();
        }
        accountChanged.setModifiedBy(account);
        accountChanged.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        accountChanged.setEmail(newEmail);
        try {
            accountFacade.edit(accountChanged);
        } catch (Exception e) {
            throw AccountException.emailConfirmationFailed();
        }
    }

    @Override
    public void confirmUnlockByToken(String jwt) throws AccountException {
        if (!jwtUnlockByMailConfirmationUtils.validateJwtToken(jwt)) {
            throw AccountException.invalidConfirmationToken();
        }
        String login;
        try {
            login = jwtUnlockByMailConfirmationUtils.getUserNameFromJwtToken(jwt);
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
        account.setActive(true);
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
    public void changePassword(ChangePasswordDto changePasswordDto) throws AppBaseException {
        Account account;
        try {
            account = accountFacade.findByLogin(changePasswordDto.getLogin());
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (AppBaseException e) {
            throw PasswordException.passwordChangeFailed();
        }
        account.setModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        account.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        if (!account.getPassword().contentEquals(hashGenerator.generateHash(changePasswordDto.getOldPassword()))) {
            throw PasswordException.currentPasswordNotMatch();
        }
        if (account.getPassword().contentEquals(hashGenerator.generateHash(changePasswordDto.getNewPassword()))) {
            throw PasswordException.passwordsNotDifferent();
        }
        account.setPassword(hashGenerator.generateHash(changePasswordDto.getNewPassword()));
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw PasswordException.passwordChangeFailed();
        }
    }

    @Override
    public void setNewPassword(SetNewPasswordDto setNewPasswordDto) throws AppBaseException {
        Account account;
        try {
            account = accountFacade.findByLogin(setNewPasswordDto.getLogin());
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (AppBaseException e) {
            throw PasswordException.passwordChangeFailed();
        }
        account.setModifiedBy(findByLogin(setNewPasswordDto.getLogin()));
        account.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        account.setFirstPasswordChange(true);
        if (account.getPassword().contentEquals(hashGenerator.generateHash(setNewPasswordDto.getNewPassword()))) {
            throw PasswordException.passwordsNotDifferent();
        }
        account.setPassword(hashGenerator.generateHash(setNewPasswordDto.getNewPassword()));
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
    public List<Account> findByActive(boolean enabled) throws AppBaseException {
        return accountFacade.findByActive(enabled);
    }

    @Override
    public void resetPassword(String login, String whoResets) throws AccountException, MailSendingException, PasswordException {
        Account account;
        try {
            account = accountFacade.findByLogin(login);
            account.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
            account.setModifiedBy(accountFacade.findByLogin(whoResets));
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
        String pass = passwordGenerator.generate(32);
        account.setPassword(hashGenerator.generateHash(pass));
        try {
            accountFacade.edit(account);
        } catch (Exception e) {
            throw PasswordException.passwordResetFailed();
        }
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
                    jwtResetPasswordConfirmation.generateJwtTokenForUsernameAndVersion(
                            login, account.getVersion()), account.getLanguage()
            );
        } catch (MailSendingException mailSendingException) {
            throw MailSendingException.editAccountMail();
        }
    }

    @Override
    public void sendResetPasswordByAdminConfirmationEmail(String login) throws AppBaseException {
        Account account;
        try {
            account = accountFacade.findByLogin(login);
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
        try {
            mailProvider.sendResetPassByAdminConfirmationMail(
                    account.getEmail(),
                    jwtResetPasswordConfirmation.generateJwtTokenForUsername(
                            login), account.getLanguage()
            );
        } catch (MailSendingException mailSendingException) {
            throw MailSendingException.editAccountMail();
        }
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
