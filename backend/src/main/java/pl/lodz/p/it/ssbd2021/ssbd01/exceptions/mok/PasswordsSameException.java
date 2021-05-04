package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.BaseException;

/**
 * Wyjątek reprezentujący błąd związany z takimi samymi wartościami starego
 * i nowego hasła podczas operacji zmiany hasła.
 */
public class PasswordsSameException extends BaseException {
    
    private static final String PASSWORDS_NOT_DIFFER = "both_passwords_are_same";

    /**
     * Tworzy nową instancję wyjątku PasswordsSameException.
     *
     * @param message wiadomość zawarta w wyjątu
     */
    public PasswordsSameException(String message) {
        super(message);
    }

    /**
     * Tworzy nową instancję wyjątku PasswordsSameException.
     *
     * @param message wiadomość zawarta w wyjątu
     * @param cause   przyczyna wystąpienia wyjątku
     */
    public PasswordsSameException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Metoda opakowująca wyjątek PasswordsSameException
     * z dedykowaną wiadomością dołączoną do niego sygnalizującą
     * powód wystąpienia wyjątku.
     *
     * @return obiekt wyjątku PasswordsSameException z wiadomością
     *         sygnalizującą jednakowość starego hasła z nowym podczas
     *         jego zmiany.
     */
    public static PasswordsSameException passwordsNotDifferent() {
        return new PasswordsSameException(PASSWORDS_NOT_DIFFER);
    }
}
