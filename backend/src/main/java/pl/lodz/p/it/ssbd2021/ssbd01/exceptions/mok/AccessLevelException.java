package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCESS_LEVEL_ADD_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCESS_LEVEL_ALREADY_ASSIGNED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCESS_LEVEL_NOT_FOUND;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCESS_LEVEL_REVOKE_FAILED;

/**
 * Wyjątek dla AccountLevel.
 */
public class AccessLevelException extends AppBaseException {

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
        return new AccessLevelException(ACCESS_LEVEL_NOT_FOUND);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę przypisania poziomu dostępu, który już jest przypisany.
     *
     * @return wyjatek AccessLevelException
     */
    public static AccessLevelException accessLevelAlreadyAssigned() {
        return new AccessLevelException(ACCESS_LEVEL_ALREADY_ASSIGNED);
    }

    /**
     * Tworzy wyjątek reprezentujący probe zdjęcia poziomu dostępu.
     *
     * @return wyjatek AccessLevelException
     */
    public static AccessLevelException accessLevelRevokeFailed() {
        return new AccessLevelException(ACCESS_LEVEL_REVOKE_FAILED);
    }

    /**
     * Tworzy wyjątek reprezentujący probe nadawania poziomu dostępu.
     *
     * @return wyjatek AccessLevelException
     */
    public static AccessLevelException accessLevelAddFailed() {
        return new AccessLevelException(ACCESS_LEVEL_ADD_FAILED);
    }

}
