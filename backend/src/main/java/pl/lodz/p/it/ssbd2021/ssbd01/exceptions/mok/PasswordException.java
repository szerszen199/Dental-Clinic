package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.CURRENT_PASSWORD_NOT_MATCH;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.NEW_PASSWORDS_NOT_MATCH;

public class PasswordException extends AppBaseException {

    /**
     * Tworzy nową instancję klasy wyjątku PasswordException.
     *
     * @param message wiadomość zawarta w wyjątku
     */
    private PasswordException(String message) {
        super(message);
    }

    /**
     * Tworzy nową instancję klasy wyjątku PasswordException.
     *
     * @param message wiadomość zawarta w wyjątku
     * @param cause   przyczyna wystąpienia wyjątku
     */
    private PasswordException(String message, Throwable cause) {
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
    public static PasswordException newPasswordsNotMatch() {
        return new PasswordException(NEW_PASSWORDS_NOT_MATCH);
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
    public static PasswordException currentPasswordNotMatch() {
        return new PasswordException(CURRENT_PASSWORD_NOT_MATCH);
    }

}
