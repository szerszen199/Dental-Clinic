package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
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
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public void createAccount(Account account, AccessLevel accessLevel) throws AppBaseException;

    /**
     * Confirm account.
     *
     * @param id id
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void confirmAccount(Long id) throws AppBaseException;


    /**
     * Confirm account.
     *
     * @param login login
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public void confirmAccount(String login) throws AppBaseException;


    /**
     * Pobiera zalogowane konto.
     *
     * @return zalogowane konto
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public Account getLoggedInAccount() throws AppBaseException;

    /**
     * Metoda służąca do blokowania konta.
     *
     * @param id identyfikator blokowanego konta
     * @throws AppBaseException bazowy wyjątek aplikacji
     */
    void lockAccount(Long id) throws AppBaseException;

    /**
     * Metoda służąca do odblokowywania konta.
     *
     * @param id identyfikator odblokowywanego konta
     * @throws AppBaseException bazowy wyjątek aplikacji
     */
    void unlockAccount(Long id) throws AppBaseException;

    /**
     * Dodaje poziom dostępu {@param level} kontowi o loginie równym {@param logon}.
     *
     * @param accessLevel poziom dostępu konta
     * @param login       login użytkownika, któremu zostanie dodany poziom dostępu
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void addAccessLevel(AccessLevel accessLevel, String login) throws AppBaseException;

    /**
     * Edytuje wlasne konto.
     *
     * @param account edytowane konto
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void editAccount(Account account) throws AppBaseException;


    /**
     * Edytuje konto innego użytkownika.
     *
     * @param account edytowane konto
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void editOtherAccount(Account account) throws AppBaseException;

    /**
     * Pobranie listy wszystkich kont.
     *
     * @return lista wszystkich kont
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    List<Account> getAllAccounts() throws AppBaseException;

    /**
     * Zmienia hasło {@param newPassword} wskazanego konta {@param account}.
     *
     * @param account     konto, którego hasło jest edytowane
     * @param oldPassword stare hasło podane przez użytkownika
     * @param newPassword nowe hasło
     * @throws AppBaseException wyjątek, gdy utrwalanie stanu konta w bazie danych
     *                       nie powiedzie się.
     */
    void changePassword(Account account, String oldPassword, String newPassword) throws AppBaseException;


    /**
     * Wyszukuje konto na podstawie loginu.
     *
     * @param login login konta do znalezienia
     * @return znalezione konto
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    Account findByLogin(String login) throws AppBaseException;

    /**
     * Resetuje hasło do konta o podanym id. Ustawia alfanumeryczne hasło
     * o długości 8 znaków.
     *
     * @param id identyfikator konta
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void resetPassword(Long id) throws AppBaseException;

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
     * @throws AppBaseException wyjątek, gdy utrwalanie stanu konta w bazie danych nie powiedzie się.
     */
    void setDarkMode(Account account, boolean isDarkMode) throws AppBaseException;
}
