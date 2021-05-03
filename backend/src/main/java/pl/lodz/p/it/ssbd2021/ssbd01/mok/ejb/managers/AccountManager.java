package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AccessLevelException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.BaseException;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs Account manager.
 */
@Local
public interface AccountManager {

    /**
     * Create account.
     *
     * @param account     nowe konto
     * @param accessLevel poziom dostępu nowego konta
     */
    public void createAccount(Account account, AccessLevel accessLevel);

    /**
     * Confirm account.
     *
     * @param id id
     */
    void confirmAccount(Long id);


    /**
     * Confirm account.
     *
     * @param login login
     */
    public void confirmAccount(String login);


    /**
     * Pobiera zalogowane konto.
     *
     * @return zalogowane konto
     */
    public Account getLoggedInAccount();

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
     * Dodaje poziom dostępu {@param level} kontowi o loginie równym {@param logon}.
     *
     * @param accessLevel poziom dostępu konta
     * @param login       login użytkownika, któremu zostanie dodany poziom dostępu
     * @throws AccessLevelException wyjątek gdy nie znaleziono poziomu dostępu
     */
    void addAccessLevel(AccessLevel accessLevel, String login) throws AccessLevelException;

    /**
     * Edytuje wlasne konto.
     *
     * @param account edytowane konto
     * @throws BaseException Base exception.
     */
    void editAccount(Account account) throws BaseException;


    /**
     * Edytuje konto innego urzytkownika.
     *
     * @param account edytowane konto
     * @throws BaseException the base exception
     */
    void editOtherAccount(Account account) throws BaseException;

    /**
     * Pobranie listy wszystkich kont.
     *
     * @return lista wszystkich kont
     */
    List<Account> getAllAccounts();

    /**
     * Zmienia hasło {@param newPassword} wskazanego konta {@param account}.
     *
     * @param account     konto, którego hasło jest edytowane
     * @param oldPassword stare hasło podane przez użytkownika
     * @param newPassword nowe hasło
     * @throws BaseException wyjątek, gdy utrwalanie stanu konta w bazie danych
     *                       nie powiedzie się.
     */
    void changePassword(Account account, String oldPassword, String newPassword) throws BaseException;
}
