package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Local;
import javax.servlet.ServletContext;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs Account manager.
 */
@Local
public interface AccountManager {

    /**
     * Utworzenie konta przy rejestracji.
     *
     * @param account        nowe konto
     * @param servletContext kontekst serwletów, służy do współdzielenia informacji
     *                       w ramach aplikacji
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void createAccount(Account account, ServletContext servletContext) throws AppBaseException;

    /**
     * usun konto.
     *
     * @param id id konta do usuniecia
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void removeAccount(Long id) throws AppBaseException;

    /**
     * Potwierdzenie konta.
     *
     * @param id id
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void confirmAccount(Long id) throws AppBaseException;


    /**
     * Potwierdzenie konta.
     *
     * @param jwt token jwt
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void confirmAccountByToken(String jwt) throws AppBaseException;

    /**
     * Metoda służąca do blokowania konta.
     *
     * @param login login blokowanego konta
     * @throws AppBaseException bazowy wyjątek aplikacji
     */
    void lockAccount(String login) throws AppBaseException;

    /**
     * Metoda służąca do odblokowywania konta.
     *
     * @param login login odblokowywanego konta
     * @throws AppBaseException bazowy wyjątek aplikacji
     */
    void unlockAccount(String login) throws AppBaseException;

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
     * @param login       login konta, którego hasło jest edytowane
     * @param oldPassword stare hasło podane przez użytkownika
     * @param newPassword nowe hasło
     * @throws AppBaseException wyjątek, gdy utrwalanie stanu konta w bazie danych                          nie powiedzie się.
     */
    void changePassword(String login, String oldPassword, String newPassword) throws AppBaseException;


    /**
     * Wyszukuje konto na podstawie loginu.
     *
     * @param login login konta do znalezienia
     * @return znalezione konto
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    Account findByLogin(String login) throws AppBaseException;

    /**
     * Wyszukuje Listę kont na podstawie aktywacji.
     *
     * @param enabled konta o danej wartosci do znalezienia
     * @return znalezione konto
     */
    List<Account> findByEnabled(boolean enabled);

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
     * @param login login konta, którego hasło ma zostać zresetowane
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void resetPassword(String login) throws AppBaseException;

    /**
     * Ustawia pole dark mode na {@param isDarkMode} w koncie {@param account}.
     *
     * @param login      login zmienianego konta
     * @param isDarkMode tryb dark mode
     * @throws AppBaseException wyjątek, gdy utrwalanie stanu konta w bazie danych nie powiedzie się.
     */
    void setDarkMode(String login, boolean isDarkMode) throws AppBaseException;


    /**
     * Update after successful login.
     *
     * @param login login
     * @param ip    ip
     * @param time  time
     * @throws AppBaseException app base exception
     */
    void updateAfterSuccessfulLogin(String login, String ip, LocalDateTime time) throws AppBaseException;

    /**
     * Update after unsuccessful login.
     *
     * @param login login
     * @param ip    ip
     * @param time  time
     * @throws AppBaseException app base exception
     */
    void updateAfterUnsuccessfulLogin(String login, String ip, LocalDateTime time) throws AppBaseException;


    /**
     * Ustawia pole language na {@param language} w koncie o logine {@param login}.
     *
     * @param login      login modyfikowanego konta
     * @param language   ustawiany język
     * @throws AppBaseException wyjątek, gdy utrwalanie stanu konta w bazie danych nie powiedzie się.
     */
    void setLanguage(String login, String language) throws AppBaseException;
}
