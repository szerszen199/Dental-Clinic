package pl.lodz.p.it.ssbd2021.ssbd01.exceptions;

public class MailSendingException extends AppBaseException {

    private static final String ACTIVATION_LINK = "Activation link couldn't be sent.";

    private static final String PASSWORD = "Mail with new password couldn't be sent.";

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
     * Metoda opakowująca wyjątek MailSendingException
     * z dedykowaną wiadomością dołączoną do niego sygnalizującą
     * powód wystąpienia wyjątku.
     *
     * @return obiekt wyjątku MailSendingException z wiadomością sygnalizującą niepowodzenie wysyłania wiadomości e-mail z linkiem aktywacyjnym do konta.
     */
    public static MailSendingException activationLink() {
        return new MailSendingException(ACTIVATION_LINK);
    }

    /**
     * Metoda opakowująca wyjątek MailSendingException
     * z dedykowaną wiadomością dołączoną do niego sygnalizującą
     * powód wystąpienia wyjątku.
     *
     * @return obiekt wyjątku MailSendingException z wiadomością sygnalizującą niepowodzenie wysyłania wiadomości e-mail
     */
    public static MailSendingException password() {
        return new MailSendingException(PASSWORD);
    }
}
