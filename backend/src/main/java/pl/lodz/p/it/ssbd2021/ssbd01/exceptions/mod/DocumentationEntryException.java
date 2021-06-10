package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import javax.persistence.OptimisticLockException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.DATABASE_OPTIMISTIC_LOCK_ERROR;

/**
 * Typ Documentation entry exception.
 */
public class DocumentationEntryException extends AppBaseException {
    /**
     * Tworzy nową instancję klasy wyjątku DocumentationEntryException.
     *
     * @param message wiadomość
     * @param cause   przyczyna wyjątku
     */
    protected DocumentationEntryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Tworzy nową instancję klasy wyjątku DocumentationEntryException.
     *
     * @param message wiadomość
     */
    protected DocumentationEntryException(String message) {
        super(message);
    }

    /**
     * Zwraca wyjątek z komunikatem {@link I18n#DOCUMENTATION_ENTRY_NOT_FOUND}.
     *
     * @return {@link DocumentationEntryException}
     */
    public static DocumentationEntryException entryNotFoundError() {
        return new DocumentationEntryException(I18n.DOCUMENTATION_ENTRY_NOT_FOUND);
    }

    /**
     * Zwraca wyjątek z komunikatem {@link I18n#INVALID_DOCTOR_DOCUMENTATION_ENTRY}.
     *
     * @return {@link DocumentationEntryException}
     */
    public static DocumentationEntryException invalidDoctorException() {
        return new DocumentationEntryException(I18n.INVALID_DOCTOR_DOCUMENTATION_ENTRY);
    }

    /**
     * Zwraca wyjątek z komunikatem {@link I18n#REMOVAL_FAILURE_ERROR}.
     *
     * @return {@link DocumentationEntryException}
     */
    public static DocumentationEntryException removalFailedError() {
        return new DocumentationEntryException(I18n.REMOVAL_FAILURE_ERROR);
    }

}
