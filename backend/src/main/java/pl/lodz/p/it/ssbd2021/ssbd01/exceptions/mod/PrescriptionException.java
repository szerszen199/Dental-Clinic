package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import javax.ejb.ApplicationException;

/**
 * Typ Prescription exception.
 */
@ApplicationException(rollback = true)
public class PrescriptionException extends AppBaseException {
    /**
     * Tworzy nową instancję klasy Prescription exception.
     *
     * @param message message
     */
    protected PrescriptionException(String message) {
        super(message);
    }

    /**
     * Tworzy nową instancję klasy Prescription exception.
     *
     * @param message message
     * @param cause   cause
     */
    protected PrescriptionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Wyjątek dla przypadku nie właściwych podanych lekarstw.
     *
     * @return prescription exception
     */
    public static PrescriptionException invalidMedicationsException() {
        return new PrescriptionException(I18n.INVALID_MEDICATIONS);
    }

    /**
     * Wyjątek dla przypadku nie powodzenia utworzenia encji.
     *
     * @return prescription exception
     */
    public static PrescriptionException creationFailureException() {
        return new PrescriptionException(I18n.PRESCRIPTION_CREATION_FAILURE);
    }
}
