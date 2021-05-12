package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.CURRENT_PASSWORD_NOT_MATCH;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.NEW_PASSWORDS_NOT_MATCH;

/**
 * Wyjątek reprezentujący błą związany z różniącymi się powtórzeniami 
 * nowego hasła podczas operacji zmiany hasła.
 */
public class PasswordsNotMatchException extends AppBaseException {

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
    public PasswordsNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Metoda opakowująca wyjątek PasswordsNotMatchException
     * z dedykowaną wiadomością dołączoną do niego sygnalizującą
     * powód wystąpienia wyjątku.
     *
     * @return obiekt wyjątku PasswordsNotMatchException z wiadomością
     *         sygnalizującą niezgodność powtórzeń haseł podczas ustalania nowego hasła.
     */
    public static PasswordsNotMatchException newPasswordsNotMatch() {
        return new PasswordsNotMatchException(NEW_PASSWORDS_NOT_MATCH);
    }

    /**
     * Metoda opakowująca wyjątek PasswordsNotMatchException
     * z dedykowaną wiadomością dołączoną do niego sygnalizującą
     * powód wystąpienia wyjątku.
     *
     * @return obiekt wyjątku PasswordsNotMatchException z wiadomością
     *         sygnalizującą niezgodność bieżącego hasła użytkownika z przekazanym
     *         przez niego.
     */
    public static PasswordsNotMatchException currentPasswordNotMatch() {
        return new PasswordsNotMatchException(CURRENT_PASSWORD_NOT_MATCH);
    }

}
