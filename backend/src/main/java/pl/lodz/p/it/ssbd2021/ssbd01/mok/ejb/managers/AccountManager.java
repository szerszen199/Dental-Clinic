package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import java.util.Set;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;

/**
 * Interfejs Account manager.
 */
@Local
public interface AccountManager {
    /**
     * Confirm account.
     *
     * @param id id
     */
    public void confirmAccount(Long id);

    /**
     * Confirm account.
     *
     * @param login login
     */
    public void confirmAccount(String login);


    /**
     * Dodanie poziomów dostępu do danego konta.
     *
     * @param account      obiekt konta
     * @param accessLevels lista poziomów dostępu
     */
    void editAccountAccessLevels(Account account, Set<AccessLevel> accessLevels);


}
