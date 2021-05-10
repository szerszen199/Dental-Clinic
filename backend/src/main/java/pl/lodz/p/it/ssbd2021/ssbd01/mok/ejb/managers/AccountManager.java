package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
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
    void createAccount(Account account, AccessLevel accessLevel);

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
    void confirmAccount(String login);


    /**
     * Pobiera zalogowane konto.
     *
     * @return zalogowane konto
     */
    Account getLoggedInAccount();

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


    /**
     * Wyszukuje konto na podstawie loginu.
     *
     * @param login login konta do znalezienia
     * @return znalezione konto
     */
    Account findByLogin(String login);

    /**
     * Resetuje hasło do konta o podanym id. Ustawia alfanumeryczne hasło
     * o długości 8 znaków.
     *
     * @param id identyfikator konta
     */
    void resetPassword(Long id);

    /**
     * Resetuje hasło podanego konta. Ustawia alfanumeryczne hasło o długości
     * 8 znaków.
     *
     * @param account konto, któego hasło ma zostać zresetowane
     */
    void resetPassword(Account account);

    /**
     * Sprawdza, czy podane konto ma aktywny poziom dostępu administratora.
     *
     * @param account sprawdzane konto
     * @return czy konto posiada aktywne uprawnienia administratora
     */
    boolean isAdmin(Account account);

    /**
     * Ustawia pole dark mode na {@param isDarkMode} w koncie {@param account}.
     *
     * @param account    zmieniane konto
     * @param isDarkMode tryb dark mode
     * @throws BaseException wyjątek, gdy utrwalanie stanu konta w bazie danych                       nie powiedzie się.
     */
    void setDarkMode(Account account, boolean isDarkMode) throws BaseException;
}
