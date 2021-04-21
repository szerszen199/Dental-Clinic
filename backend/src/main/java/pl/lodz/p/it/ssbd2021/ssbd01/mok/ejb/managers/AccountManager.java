package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import java.util.Set;
import javax.ejb.Local;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AccessLevelException;

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
     * Dodaje poziom dostępu {@param level} kontowi o id równym {@param id}.
     *
     * @param id    id użytkownika, któremu zostanie dodany poziom dostępu
     * @param level dodawany poziom odstępu
     *
     * @throws AccessLevelException wyjątek gdy nie znaleziono poziomu dostępu
     */
    void addAccessLevel(Long id, String level) throws AccessLevelException;

    /**
     * Dodaje poziom dostępu {@param level} kontowi o loginie równym {@param logon}.
     *
     * @param login login użytkownika, któremu zostanie dodany poziom dostępu
     * @param level dodawany poziom odstępu
     *
     * @throws AccessLevelException wyjątek gdy nie znaleziono poziomu dostępu
     */
    void addAccessLevel(String login, String level) throws AccessLevelException;


}
