package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import java.util.Set;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.BaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AccessLevelException;

/**
 * Interfejs Account manager.
 */
@Local
public interface AccountManager {

    /**
     * Create account.
     *
     * @param account nowe konto
     * @param accessLevel poziom dostępu nowego konta
     */
    public void createAccount(Account account, AccessLevel accessLevel);

    /**
     * Confirm account.
     *
     * @param id id
     */
    void confirmAccount(Long id);

    void editAccount(Long id,Account account) throws BaseException;

    /**
     * Confirm account.
     *
     * @param login login
     */
    void confirmAccount(String login);

    /**
     * Metoda służąca do blokowania konta.
     *
     * @param id identyfikator blokowanego konta
     * @throws BaseException bazowy wyjątek aplikacji
     */
    void lockAccount(Long id) throws BaseException;

    /**
     * Metoda służąca do odblokowywania konta.
     *
     * @param id identyfikator odblokowywanego konta
     * @throws BaseException bazowy wyjątek aplikacji
     */
    void unlockAccount(Long id) throws BaseException;

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
