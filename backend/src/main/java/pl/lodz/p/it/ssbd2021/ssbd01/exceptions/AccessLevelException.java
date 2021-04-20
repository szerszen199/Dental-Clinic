package pl.lodz.p.it.ssbd2021.ssbd01.exceptions;

/**
 * Wyjątek dla AccountLevel.
 */
public class AccessLevelException extends Throwable {

    /**
     * Tworzy nową instancję klasy wyjątku AccessLevelException.
     *
     * @param message wiadomość zawarta w wyjątku
     * @param cause   przyczyna wystąpienia wyjątku
     */
    public AccessLevelException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Tworzy nową instancję klasy wyjątku AccessLevelException.
     *
     * @param message wiadomość zawarta w wyjątku
     */
    public AccessLevelException(String message) {
        super(message);
    }

    /**
     * Tworzy wyjątek reprezentujący brak danego poziomu dostępu.
     *
     * @return wyjatek AccessLevelException
     */
    public static AccessLevelException noSuchAccessLevel() {
        return new AccessLevelException("Nie istnieje taki poziom dostępu");
    }
}
