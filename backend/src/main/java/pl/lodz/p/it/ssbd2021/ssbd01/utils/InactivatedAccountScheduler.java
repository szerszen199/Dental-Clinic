package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtEmailConfirmationUtils;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Startup
@Singleton
public class InactivatedAccountScheduler {

    @Inject
    private AccountManager accountManager;
    @Inject
    private PropertiesLoader propertiesLoader;
    @Inject
    private MailProvider mailProvider;
    @EJB
    private JwtEmailConfirmationUtils jwtEmailConfirmationUtils;

    /**
     * automatycznie kolejkuje usuwanie nieaktywnych kont oraz w połowie czasu usunięcia wysyła maila z przypomnieniem.
     *
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @Schedule(hour = "*", minute = "1", second = "1", info = "Every 1 hour timer")
    public void automaticallyScheduled() throws AppBaseException {
        List<Account> notEnabledAccounts = accountManager.findByEnabled(false);
        for (Account notEnabledAccount : notEnabledAccounts) {
            Long time = Duration.between(notEnabledAccount.getCreationDateTime(), LocalDateTime.now()).toMillis();
            if (time >= propertiesLoader.getDeleteInactiveAccount()) {
                accountManager.removeAccount(notEnabledAccount.getId());
            } else if (time >= (propertiesLoader.getDeleteInactiveAccount() / 2) && !notEnabledAccount.getEmailrecall()) {
                accountManager.setEmailRecallTrue(notEnabledAccount.getLogin());
                mailProvider.sendActivationMail(
                        notEnabledAccount.getEmail(),
                        "path",
                        jwtEmailConfirmationUtils.generateRegistrationConfirmationJwtTokenForUser(notEnabledAccount.getLogin())
                );
            }
        }
    }
}
