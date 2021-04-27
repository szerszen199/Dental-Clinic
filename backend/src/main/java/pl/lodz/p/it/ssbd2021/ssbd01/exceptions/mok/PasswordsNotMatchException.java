package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.BaseException;

/**
 * Wyjątek reprezentujący błą związany z różniącymi się powtórzeniami 
 * nowego hasła podczas operacji zmiany hasła.
 */
public class PasswordsNotMatchException extends BaseException {
    
    public static final String NEW_PASSWORDS_NOT_MATCH = "new_passwords_do_not_match";
    public static final String CURRENT_PASSWORD_NOT_MATCH = "current_password_do_not_match";

    /**
     * Tworzy nową instancję wyjątku PasswordsNotMatchException.
     *
     * @param message wiadomość zawarta w wyjątu
     */
    public PasswordsNotMatchException(String message) {
        super(message);
    }

    /**
     * Tworzy nową instancję wyjątku PasswordsNotMatchException.
     *
     * @param message wiadomość zawarta w wyjątu
     * @param cause   przyczyna wystąpienia wyjątku
     */
    protected PasswordsNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
