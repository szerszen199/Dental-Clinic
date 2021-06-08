package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.EditAnotherAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.EditOwnAccountRequestDTO;

import javax.ejb.Local;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfejs menadżera konta.
 */
@Local
public interface AccountManager {

    /**
     * Utworzenie konta przy rejestracji.
     *
     * @param account nowe konto
     * @throws AccountException     wyjątek typu AccountException
     * @throws MailSendingException wyjątek typu MailSendingException
     */
    void createAccount(Account account) throws AccountException, MailSendingException;

    /**
     * usun konto.
     *
     * @param id id konta do usuniecia
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void removeAccount(Long id) throws AppBaseException;

    /**
     * Ustawia EmailRecall na false.
     *
     * @param login login konta dla którego zostanie zmieniony EmailReccal na true
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void setEmailRecallTrue(String login) throws AppBaseException;

    /**
     * Ustawia Active na false.
     *
     * @param login login konta dla którego zostanie zmieniony EmailReccal na true
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void setActiveFalse(String login) throws AppBaseException;

    /**
     * Potwierdzenie konta.
     *
     * @param jwt token jwt
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void confirmAccountByToken(String jwt) throws AppBaseException;

    /**
     * reset hasla konta.
     *
     * @param login login
     * @throws AccountException     wyjątek typu AccountException
     * @throws MailSendingException wyjątek typu MailSendingException
     * @throws PasswordException    wyjątek typu PasswordException
     */
    void resetPasswordByToken(String login) throws AccountException, MailSendingException, PasswordException;

    /**
     * Wysłanie maila do resetowania hasła przez administratora.
     *
     * @param login login
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void sendResetPasswordConfirmationEmail(String login) throws AppBaseException;

    /**
     * Wysłanie maila do resetowania hasła przez administratora.
     *
     * @param login login
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void sendResetPasswordByAdminConfirmationEmail(String login) throws AppBaseException;

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
     * @param editOwnAccountRequestDTO dto edycji danych własengo konta
     * @throws AccountException     wyjątek typu AccountException
     * @throws MailSendingException wyjątek typu MailSendingException
     */
    void editOwnAccount(EditOwnAccountRequestDTO editOwnAccountRequestDTO) throws
            AccountException,MailSendingException;


    /**
     * Edytuje konto innego użytkownika.
     *
     * @param editAnotherAccountRequestDTO dto edycji danych innego konta
     * @throws AccountException     wyjątek typu AccountException
     * @throws MailSendingException wyjątek typu MailSendingException
     */
    void editOtherAccount(EditAnotherAccountRequestDTO editAnotherAccountRequestDTO) throws AccountException,MailSendingException;

    /**
     * Potwierdzenie zmiany maila.
     *
     * @param jwt token jwt
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void confirmMailChangeByToken(String jwt) throws AppBaseException;


    /**
     * Potwierdzenie odblokowania konta.
     *
     * @param jwt token jwt
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void confirmUnlockByToken(String jwt) throws AppBaseException;

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
     * Zmienia hasło {@param newPassword} wskazanego konta {@param account}.
     *
     * @param login       login konta, którego hasło jest edytowane
     * @param newPassword nowe hasło
     * @throws AppBaseException wyjątek, gdy utrwalanie stanu konta w bazie danych                          nie powiedzie się.
     */
    void setNewPassword(String login, String newPassword) throws AppBaseException;

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
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    List<Account> findByEnabled(boolean enabled) throws AppBaseException;

    /**
     * Wyszukuje Listę kont na podstawie aktywacji.
     *
     * @param enabled konta o danej wartosci do znalezienia
     * @return znalezione konto
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    List<Account> findByActive(boolean enabled) throws AppBaseException;

    /**
     * Resetuje hasło podanego konta. Ustawia alfanumeryczne hasło o długości
     * 8 znaków.
     *
     * @param accountToReset login konta, którego hasło ma zostać zresetowane
     * @param whoResets      login konta resetującego
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    void resetPassword(String accountToReset, String whoResets) throws AppBaseException;

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
     * @param login    login modyfikowanego konta
     * @param language ustawiany język
     * @throws AppBaseException wyjątek, gdy utrwalanie stanu konta w bazie danych nie powiedzie się.
     */
    void setLanguage(String login, String language) throws AppBaseException;

    /**
     * Sprawdza czy ostatnia transakcja się powiodła.
     *
     * @return true jeśli ostatnia transakcja się nie powiodła, false w przeciwnym wypadku.
     */
    boolean isLastTransactionRollback();
}
