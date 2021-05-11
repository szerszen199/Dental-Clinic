package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.BaseException;

/**
 * Wyjątek reprezentujący błąd związany z niewystarczającą długością nowego hasła
 * podczas operacji zmiany hasła.
 */
public class PasswordTooShortException extends BaseException {
    
    private static final String PASSWORD_TOO_SHORT = "password_too_short";

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
