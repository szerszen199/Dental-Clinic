package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.PASSWORD_TOO_SHORT;

/**
 * Wyjątek reprezentujący błąd związany z niewystarczającą długością nowego hasła
 * podczas operacji zmiany hasła.
 */
public class PasswordTooShortException extends AppBaseException {

    /**
     * Tworzy nową instancję wyjątku PasswordTooShortException.
     *
     * @param message wiadomość zawarta w wyjątu
     */
    public PasswordTooShortException(String message) {
        super(message);
    }

    /**
     * Tworzy nową instancję wyjątku PasswordTooShortException.
     *
     * @param message wiadomość zawarta w wyjątu
     * @param cause   przyczyna wystąpienia wyjątku
     */
    public PasswordTooShortException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Metoda opakowująca wyjątek PasswordTooShortException
     * z dedykowaną wiadomością dołączoną do niego sygnalizującą
     * powód wystąpienia wyjątku.
     *
     * @return obiekt wyjątku PasswordTooShortException z wiadomością sygnalizującą
     *         niewystarczającą długość hasła podczas jego tworzenia
     */
    public static PasswordTooShortException passwordTooShort() {
        return new PasswordTooShortException(PASSWORD_TOO_SHORT);
    }
}
