package pl.lodz.p.it.ssbd2021.ssbd01.exceptions;

/**
 * Wyjątek.
 */
public class DataValidationException extends BaseException {

    /**
     * Tworzy nową instancję klasy wyjątku DataValidationException.
     *
     * @param message wiadomość zawarta w wyjątku
     * @param cause   przyczyna wystąpienia wyjątku
     */
    public DataValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Tworzy nową instancję klasy wyjątku DataValidationException.
     *
     * @param message wiadomość zawarta w wyjątku
     */
    public DataValidationException(String message) {
        super(message);
    }

    /**
     * Tworzy wyjątek reprezentujący brak danego poziomu dostępu.
     *
     * @return wyjatek DataValidationException
     */
    public static DataValidationException noSuchAccessLevel() {
        return new DataValidationException("Nie istnieje taki poziom dostępu");
    }
}
