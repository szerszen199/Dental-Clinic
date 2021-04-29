package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.BaseException;

/**
 * Wyjątek reprezentujący błąd związany z niewystarczającą długością nowego hasła
 * podczas operacji zmiany hasła.
 */
public class PasswordTooShortException extends BaseException {
    
    public static final String PASSWORD_TOO_SHORT = "password_too_short";

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
    protected PasswordTooShortException(String message, Throwable cause) {
        super(message, cause);
    }
}
