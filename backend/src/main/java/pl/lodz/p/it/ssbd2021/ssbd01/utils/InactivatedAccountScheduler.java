package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccessLevelManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JWTRegistrationConfirmationUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtUnlockByMailConfirmationUtils;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Klasa planista nieaktywnych kont.
 */
@Startup
@Singleton
public class InactivatedAccountScheduler {

    @Inject
    private AccountManager accountManager;
    @Inject
    private AccessLevelManager accessLevelManager;
    @Inject
    private PropertiesLoader propertiesLoader;
    @Inject
    private MailProvider mailProvider;
    @Inject
    private JWTRegistrationConfirmationUtils jwtRegistrationConfirmationUtils;
    @Inject
    private JwtUnlockByMailConfirmationUtils jwtUnlockByMailConfirmationUtils;

    /**
     * automatycznie kolejkuje usuwanie nieaktywnych kont oraz w połowie czasu usunięcia wysyła maila z przypomnieniem.
     *
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @Schedule(hour = "*", minute = "1", second = "1", info = "Every hour timer")
    public void automaticallyScheduleInactivatedAccounts() throws AppBaseException {
        List<Account> notEnabledAccounts = accountManager.findByEnabled(false);
        for (Account notEnabledAccount : notEnabledAccounts) {
            Long time = Duration.between(notEnabledAccount.getCreationDateTime(), LocalDateTime.now()).toMillis();
            if (time >= propertiesLoader.getDeleteInactiveAccountTimeDelay()) {
                mailProvider.sendAccountDeletedByScheduler(notEnabledAccount.getEmail(), notEnabledAccount.getLanguage());
                accessLevelManager.deleteAccessLevelsByAccountId(notEnabledAccount.getId());
                accountManager.removeAccount(notEnabledAccount.getId());
            } else if (time >= (propertiesLoader.getDeleteInactiveAccountTimeDelay() / 2) && !notEnabledAccount.getEmailRecall()) {
                accountManager.setEmailRecallTrue(notEnabledAccount.getLogin());
                mailProvider.sendActivationMail(notEnabledAccount.getEmail(), jwtRegistrationConfirmationUtils
                        .generateJwtTokenForUsername(notEnabledAccount.getLogin()), notEnabledAccount.getLanguage()
                );
            }
        }
    }

    /**
     * automatycznie kolejkuje blokowanie konta nie aktywnego i wysyła maila z linkiem do jego odblokowania.
     *
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @Schedule(hour = "1", minute = "1", second = "1", info = "Every day timer")
    public void automaticallyScheduleAccountLock() throws AppBaseException {
        List<Account> activeAccounts = accountManager.findByActive(true);
        for (Account activeAccount : activeAccounts) {
            if (activeAccount.getLastSuccessfulLogin() != null) {
                Long time = Duration.between(activeAccount.getLastSuccessfulLogin(), LocalDateTime.now()).toMillis();
                if (time >= propertiesLoader.getDeactivateInactiveAccountTimeDelay()) {
                    accountManager.setActiveFalse(activeAccount.getLogin());
                    mailProvider.sendAccountLockedByScheduler(activeAccount.getEmail(),
                            jwtUnlockByMailConfirmationUtils.generateJwtTokenForUsername(activeAccount.getLogin()),
                            activeAccount.getLanguage());
                }
            }
        }
    }
}
