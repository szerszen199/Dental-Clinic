package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

public class MedicalDocumentationException extends AppBaseException {
    protected MedicalDocumentationException(String message) {
        super(message);
    }

    protected MedicalDocumentationException(String message, Throwable cause) {
        super(message, cause);
    }

    public static DocumentationEntryException noSuchMedicalDocumentation() {
        return new DocumentationEntryException(I18n.MEDICAL_DOCUMENTATION_NOT_FOUND);
    }

    public static DocumentationEntryException noSuchMedicalDocumentation(Throwable cause) {
        return new DocumentationEntryException(I18n.MEDICAL_DOCUMENTATION_NOT_FOUND, cause);
    }

}
