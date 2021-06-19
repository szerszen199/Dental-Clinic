package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import javax.ejb.ApplicationException;

/**
 * Typ wyjątku dla dukumentacji medycznej.
 */
@ApplicationException(rollback = true)
public class MedicalDocumentationException extends AppBaseException {
    /**
     * Tworzy nową instancję klasy Medical documentation exception.
     *
     * @param message wiadomość
     */
    protected MedicalDocumentationException(String message) {
        super(message);
    }

    /**
     * Tworzy nową instancję klasy Medical documentation exception.
     *
     * @param message wiadomość
     * @param cause   powód rzucenia wyjątku
     */
    protected MedicalDocumentationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Wyjątek gdy wskazana dokumentacja medyczna nie istnieje.
     *
     * @return wyjątek typu DocumentationEntryException
     */
    public static MedicalDocumentationException noSuchMedicalDocumentation() {
        return new MedicalDocumentationException(I18n.MEDICAL_DOCUMENTATION_NOT_FOUND);
    }

    /**
     * Wyjątek gdy wskazana dokumentacja medyczna nie istnieje.
     *
     * @param cause powód rzucenia wyjątku
     * @return wyjątek typu DocumentationEntryException
     */
    public static MedicalDocumentationException noSuchMedicalDocumentation(Throwable cause) {
        return new MedicalDocumentationException(I18n.MEDICAL_DOCUMENTATION_NOT_FOUND, cause);
    }

    /**
     * Wyjątek gdy wskazana dokumentacja medyczna nie istnieje.
     *
     * @return wyjątek typu DocumentationEntryException
     */
    public static MedicalDocumentationException medicalDocumentationCreationFailed() {
        return new MedicalDocumentationException(I18n.MEDICAL_DOCUMENTATION_CREATION_FAILED);
    }

}
