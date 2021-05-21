package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;

@Startup
@Singleton
public class InactivatedAccountDeleter {

    @Inject
    private AccountManager accountManager;
    @Inject
    private PropertiesLoader propertiesLoader;

    /**
     * automatycznie kolejkuje usuwanie nieaktywnych kont.
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @Schedule(hour = "*", minute = "1", second = "1", info = "Every 1 hour timer")
    public void automaticallyScheduled() throws AppBaseException {
        List<Account> notEnabledAccounts = accountManager.findByEnabled(false);
        for (Account notEnabledAccount : notEnabledAccounts) {
            if (Duration.between(notEnabledAccount.getCreationDateTime(), LocalDateTime.now()).toMillis() >= propertiesLoader.getDeleteInactiveAccount()) {
                accountManager.removeAccount(notEnabledAccount.getId());
            }
        }
    }
}
