package pl.lodz.p.it.ssbd2021.ssbd01.exceptions;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.EDIT_ACCOUNT_MAIL;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.MAIL_ACCOUNT_LOCK_SEND_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.MAIL_ACTIVATION_CONFIRMATION_SEND_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.MAIL_ACTIVATION_LINK_SEND_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.PASSWORD_RESET_MAIL_SENT_ERROR;

/**
 * Typ Mail sending exception - klasa wyjątków związanych z mailem.
 */
public class MailSendingException extends AppBaseException {

    /**
     * Tworzy nową instancję wyjątku MailSendingException.
     *
     * @param message wiadomość zawarta w wyjątu
     */
    public MailSendingException(String message) {
        super(message);
    }

    /**
     * Tworzy nową instancję wyjątku MailSendingException.
     *
     * @param message wiadomość zawarta w wyjątu
     * @param cause   przyczyna wystąpienia wyjątku
     */
    public MailSendingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Metoda opakowująca wyjątek MailSendingException z informacją, o błędzie wysyłania linku aktywacyjnego.
     *
     * @return obiekt wyjątku MailSendingException z wiadomością sygnalizującą niepowodzenie wysyłania wiadomości e-mail z linkiem aktywacyjnym do konta.
     */
    public static MailSendingException activationLink() {
        return new MailSendingException(MAIL_ACTIVATION_LINK_SEND_ERROR);
    }

    /**
     * Metoda opakowująca wyjątek MailSendingException z informacją o błędzie wysyłania maila po resecie hasła.
     *
     * @return obiekt wyjątku MailSendingException z wiadomością sygnalizującą niepowodzenie wysyłania wiadomości e-mail podczas resetu hasła użytkownika.
     */
    public static MailSendingException passwordResetMail() {
        return new MailSendingException(PASSWORD_RESET_MAIL_SENT_ERROR);
    }

    /**
     * Metoda opakowująca wyjątek MailSendingException z informacją o błędzie wysyłania maila po edycji konta.
     *
     * @return obiekt wyjątku MailSendingException z wiadomością sygnalizującą niepowodzenie wysyłania wiadomości e-mail podczas edycji konta.
     */
    public static MailSendingException editAccountMail() {
        return new MailSendingException(EDIT_ACCOUNT_MAIL);
    }

    /**
     * Metoda opakowująca wyjątek MailSendingExceptionz informacją o błędzie wysyłania maila o blokadzie konta.
     *
     * @return obiekt wyjątku MailSendingException z wiadomością sygnalizującą niepowodzenie wysyłania wiadomości e-mail informacją o zablokowanym koncie.
     */
    public static MailSendingException accountLock() {
        return new MailSendingException(MAIL_ACCOUNT_LOCK_SEND_ERROR);
    }

    /**
     * Metoda opakowująca wyjątek MailSendingException z informacją o błędzie wysyłania maila potwierdzającego aktywację kotna.
     *
     * @return obiekt wyjątku MailSendingException z wiadomością sygnalizującą niepowodzenie wysyłania wiadomości e-mail informacją o aktywacji konta.
     */
    public static MailSendingException activationConfirmation() {
        return new MailSendingException(MAIL_ACTIVATION_CONFIRMATION_SEND_ERROR);
    }

    /** Metoda opakowująca wyjątek MailSendingException z informacją o błędzie wysyłania maila.
     *
     * @return obiekt wyjątku MailSendingException z wiadomością sygnalizującą niepowodzenie wysyłania wiadomości e-mail.
     */
    public static MailSendingException mailFailed() {
        return new MailSendingException(I18n.MAIL_FAILED);
    }
}
