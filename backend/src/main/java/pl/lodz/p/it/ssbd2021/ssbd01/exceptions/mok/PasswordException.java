package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.CURRENT_PASSWORD_NOT_MATCH;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.NEW_PASSWORDS_NOT_MATCH;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.PASSWORDS_NOT_DIFFER;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.PASSWORD_CHANGE_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.PASSWORD_RESET_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.PASSWORD_TOO_SHORT;

/**
 * Typ Password exception - wyjątki powiązane z hasłem.
 */
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
     * Metoda opakowująca wyjątek PasswordException
     * z informacją, że hasła nie są identyczne.
     *
     * @return obiekt wyjątku PasswordException z wiadomością sygnalizującą niezgodność powtórzeń haseł podczas ustalania nowego hasła.
     */
    public static PasswordException newPasswordsNotMatch() {
        return new PasswordException(NEW_PASSWORDS_NOT_MATCH);
    }

    /**
     * Metoda opakowująca wyjątek PasswordException z informacją, że hasło nie pasuje do aktualnego hasła.
     *
     * @return obiekt wyjątku PasswordException z wiadomością sygnalizującą niezgodność bieżącego hasła użytkownika z przekazanym przez niego.
     */
    public static PasswordException currentPasswordNotMatch() {
        return new PasswordException(CURRENT_PASSWORD_NOT_MATCH);
    }

    /**
     * Metoda opakowująca wyjątek PasswordException z informacją, że hasła się nie różnią.
     *
     * @return obiekt wyjątku PasswordException z wiadomością sygnalizującą jednakowość starego hasła z nowym podczas jego zmiany.
     */
    public static PasswordException passwordsNotDifferent() {
        return new PasswordException(PASSWORDS_NOT_DIFFER);
    }

    /**
     * Metoda opakowująca wyjątek PasswordException z informacją, że hasło jest za krótkie.
     *
     * @return obiekt wyjątku PasswordException z wiadomością sygnalizującą niewystarczającą długość hasła podczas jego tworzenia
     */
    public static PasswordException passwordTooShort() {
        return new PasswordException(PASSWORD_TOO_SHORT);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę zresetowania hasła zakończoną niepowodzeniem.
     *
     * @return wyjątek typu PasswordException
     */
    public static PasswordException passwordResetFailed() {
        return new PasswordException(PASSWORD_RESET_FAILED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę zmiany hasła użytkownika.
     *
     * @return wyjątek typu PasswordException
     */
    public static PasswordException passwordChangeFailed() {
        return new PasswordException(PASSWORD_CHANGE_FAILED);
    }

}
