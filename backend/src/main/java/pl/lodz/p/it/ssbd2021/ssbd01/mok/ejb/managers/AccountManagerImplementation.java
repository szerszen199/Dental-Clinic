package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AdminData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.ReceptionistData;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordsNotMatchException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordsSameException;
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
    public void createAccount(Account account) throws AppBaseException {
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

        account.setCreatedBy(account);
        accountFacade.create(account);

        mailProvider.sendActivationMail(
                account.getEmail(),
                jwtRegistrationConfirmationUtils.generateJwtTokenForUsername(account.getLogin())
        );
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
    public void confirmAccountByToken(String jwt) throws AppBaseException {
        if (!jwtEmailConfirmationUtils.validateJwtToken(jwt)) {
            throw AccountException.invalidConfirmationToken();
        }
        try {
            String login = jwtEmailConfirmationUtils.getUserNameFromJwtToken(jwt);
            Account account = accountFacade.findByLogin(login);
            account.setEnabled(true);
            accountFacade.edit(account);
            mailProvider.sendActivationConfirmationMail(accountFacade.findByLogin(login).getEmail());
        } catch (AppBaseException | ParseException e) {
            throw AccountException.noSuchAccount(e);
        }
    }

    @Override
    public void lockAccount(String login) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setActive(false);
        account.setLastBlockUnlockDateTime(LocalDateTime.now());
        account.setLastBlockUnlockIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        if (!loggedInAccountUtil.getLoggedInAccountLogin().equals(propertiesLoader.getAnonymousUserName())) {
            // TODO: Zastanowić się i ustawić pole modifiedBy po zablokowaniu konta przez system po nieudanych logowaniach
            account.setLastBlockUnlockModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        }
        accountFacade.edit(account);
    }

    @Override
    public void unlockAccount(String login) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setLastBlockUnlockDateTime(LocalDateTime.now());
        account.setLastBlockUnlockIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        account.setLastBlockUnlockModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        account.setActive(true);
        accountFacade.edit(account);
    }

    @Override
    // Nie widzę logicznego poziomu dlaczego miałoby tutaj być podawane konto zamiast DTO, jak mi dacie powód to to zmienie
    public void editOwnAccount(EditOwnAccountRequestDTO editOwnAccountRequestDTO) throws AppBaseException {
        Account toBeModified = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        if (!toBeModified.getVersion().equals(editOwnAccountRequestDTO.getVersion())) {
            throw AppBaseException.versionMismatchException();
        }
        toBeModified.setModifiedBy(toBeModified);
        toBeModified.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        if (editOwnAccountRequestDTO.getFirstName() != null) {
            toBeModified.setFirstName(editOwnAccountRequestDTO.getFirstName());
        }
        if (editOwnAccountRequestDTO.getLastName() != null) {
            toBeModified.setLastName(editOwnAccountRequestDTO.getLastName());
        }
        if (editOwnAccountRequestDTO.getEmail() != null && !toBeModified.getEmail().equals(editOwnAccountRequestDTO.getEmail())) {
            toBeModified.setEmail(editOwnAccountRequestDTO.getEmail());
            // TODO: 21.05.2021 Wysylac maila dopieo kiedy edit się powiódł?
            mailProvider.sendEmailChangeConfirmationMail(
                    editOwnAccountRequestDTO.getEmail(),
                    jwtEmailConfirmationUtils.generateEmailChangeConfirmationJwtTokenForUser(
                            loggedInAccountUtil.getLoggedInAccountLogin(), editOwnAccountRequestDTO.getEmail())
            );
        }
        toBeModified.setPhoneNumber(editOwnAccountRequestDTO.getPhoneNumber());
        toBeModified.setPesel(editOwnAccountRequestDTO.getPesel());
        toBeModified.setModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        accountFacade.edit(toBeModified);
    }

    // TODO: 21.05.2021 Bardzo kusi wyrzucić to i edycje swojego konta do wspólnej metody
    //  Jak komus sie chce zapraszam
    @Override
    public void editOtherAccount(EditAnotherAccountRequestDTO editAnotherAccountRequestDTO) throws AppBaseException {
        Account toBeModified = accountFacade.findByLogin(editAnotherAccountRequestDTO.getLogin());
        if (!toBeModified.getVersion().equals(editAnotherAccountRequestDTO.getVersion())) {
            throw AppBaseException.versionMismatchException();
        }
        toBeModified.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        toBeModified.setModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        if (editAnotherAccountRequestDTO.getFirstName() != null) {
            toBeModified.setFirstName(editAnotherAccountRequestDTO.getFirstName());
        }
        if (editAnotherAccountRequestDTO.getLastName() != null) {
            toBeModified.setLastName(editAnotherAccountRequestDTO.getLastName());
        }
        if (editAnotherAccountRequestDTO.getEmail() != null && !toBeModified.getEmail().equals(editAnotherAccountRequestDTO.getEmail())) {
            // TODO: 21.05.2021 Wysylac maila dopieo kiedy edit się powiódł?
            // TODO: 21.05.2021 Również todo czy jak admin edytuje konto to też mamy wysyłać maila? Chyba niee
            // mailProvider.sendEmailChangeConfirmationMail(
            //        editAnotherAccountRequestDTO.getEmail(),
            //        servletContext.getContextPath(),
            //        jwtEmailConfirmationUtils.generateEmailChangeConfirmationJwtTokenForUser(
            //                loggedInAccountUtil.getLoggedInAccountLogin(),
            //                editAnotherAccountRequestDTO.getEmail())
            // );
        }
        if (editAnotherAccountRequestDTO.getPhoneNumber() != null) {
            toBeModified.setPhoneNumber(editAnotherAccountRequestDTO.getPhoneNumber());
        }
        if (editAnotherAccountRequestDTO.getPesel() != null) {
            toBeModified.setPesel(editAnotherAccountRequestDTO.getPesel());
        }
        toBeModified.setModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        accountFacade.edit(toBeModified);
    }

    @Override
    public void confirmMailChangeByToken(String jwt) throws AppBaseException {
        if (!jwtEmailConfirmationUtils.validateJwtToken(jwt)) {
            throw AccountException.invalidConfirmationToken();
        }
        try {
            String login = jwtEmailConfirmationUtils.getUsernameFromToken(jwt);
            String newEmail = jwtEmailConfirmationUtils.getEmailFromToken(jwt);
            Account account = accountFacade.findByLogin(login);
            account.setModifiedBy(account);
            account.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
            account.setEmail(newEmail);
            accountFacade.edit(account);
        } catch (AppBaseException | ParseException e) {
            throw AccountException.noSuchAccount(e);
        } catch (NullPointerException e) {
            throw AccountException.mailConfirmationParsingError(e);
        }
    }

    @Override
    public List<Account> getAllAccounts() throws AppBaseException {
        return accountFacade.findAll();
    }

    @Override
    public void changePassword(String login, String oldPassword, String newPassword) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setModifiedBy(findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        account.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        if (!account.getPassword().contentEquals(hashGenerator.generateHash(oldPassword))) {
            throw PasswordsNotMatchException.currentPasswordNotMatch();
        }
        if (account.getPassword().contentEquals(hashGenerator.generateHash(newPassword))) {
            throw PasswordsSameException.passwordsNotDifferent();
        }
        account.setPassword(hashGenerator.generateHash(newPassword));
        accountFacade.edit(account);
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void resetPassword(String accountToReset, String whoResets) throws AppBaseException {
        Account account = accountFacade.findByLogin(accountToReset);
        account.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        account.setModifiedBy(accountFacade.findByLogin(whoResets));
        // TODO: 21.05.2021 Dlugosc do zmiennej w pliku konfiguracyjnym
        String pass = passwordGenerator.generate(32);
        account.setPassword(hashGenerator.generateHash(pass));
        accountFacade.edit(account);
        mailProvider.sendGeneratedPasswordMail(account.getEmail(), pass);
        // TODO: send mail with new password
    }

    @Override
    public void sendResetPasswordConfirmationEmail(String login) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        mailProvider.sendResetPassConfirmationMail(
                account.getEmail(),
                jwtResetPasswordConfirmation.generateJwtTokenForUsername(
                        login)
        );
        // TODO: send mail with new password
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void setLastSuccessfulLoginIp(Account account, String ip) throws AppBaseException {
        account.setLastSuccessfulLoginIp(ip);
        accountFacade.edit(account);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void setLastSuccessfulLoginTime(Account account, LocalDateTime time) throws AppBaseException {
        account.setLastSuccessfulLogin(time);
        accountFacade.edit(account);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void increaseInvalidLoginCount(Account account) throws AppBaseException {
        account.setUnsuccessfulLoginCounter(account.getUnsuccessfulLoginCounter() + 1);
        accountFacade.edit(account);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void zeroInvalidLoginCount(Account account) throws AppBaseException {
        account.setUnsuccessfulLoginCounter(0);
        accountFacade.edit(account);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void setLastUnsuccessfulLoginTime(Account account, LocalDateTime time) throws AppBaseException {
        account.setLastUnsuccessfulLogin(time);
        accountFacade.edit(account);
    }

    @Override
    public void updateAfterSuccessfulLogin(String login, String ip, LocalDateTime time) throws AppBaseException {
        Account account = findByLogin(login);
        setLastSuccessfulLoginIp(account, ip);
        setLastSuccessfulLoginTime(account, time);
        zeroInvalidLoginCount(account);
        accountFacade.edit(account);
    }

    @Override
    public void updateAfterUnsuccessfulLogin(String login, String ip, LocalDateTime time) throws AppBaseException {
        Account account = findByLogin(login);
        setLastUnsuccessfulLoginIp(account, ip);
        setLastUnsuccessfulLoginTime(account, time);
        increaseInvalidLoginCount(account);
        accountFacade.edit(account);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    private void setLastUnsuccessfulLoginIp(Account account, String ip) throws AppBaseException {
        account.setLastUnsuccessfulLoginIp(ip);
        accountFacade.edit(account);
    }

    @Override
    public void setDarkMode(String login, boolean isDarkMode) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setModifiedBy(account);
        account.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        account.setDarkMode(isDarkMode);
        accountFacade.edit(account);
    }

    @Override
    public void setLanguage(String login, String language) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setModifiedBy(account);
        account.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        account.setLanguage(language);
        accountFacade.edit(account);
    }

}
