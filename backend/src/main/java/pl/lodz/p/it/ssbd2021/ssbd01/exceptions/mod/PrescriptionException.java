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

    /**
     * Wyjątek w przypadku nie odnalezienia recepty.
     *
     * @return wyjątek PrescriptionException
     */
    public static PrescriptionException prescriptionNotFound() {
        return new PrescriptionException(I18n.PRESCRIPTION_NOT_FOUND);
    }

    /**
     * Wyjątek w przypadku braku zgodności wersji recepty.
     *
     * @return wyjątek PrescriptionException
     */
    public static PrescriptionException versionMismatch() {
        return new PrescriptionException(I18n.VERSION_MISMATCH);
    }

    /**
     * Wyjątek w przypadku niepowodzenia edycji recepty.
     *
     * @return wyjątek PrescriptionException
     */
    public static PrescriptionException prescriptionEditFailed() {
        return new PrescriptionException(I18n.PRESCRIPTION_EDIT_FAILED);
    }

    /**
     * Wyjątek w przypadku niepowodzenia odnalezienia konta edytującego receptę.
     *
     * @param cause powód wyjątku
     * @return wyjątek PrescriptionException
     */
    public static PrescriptionException accountNotFound(Throwable cause) {
        return new PrescriptionException(I18n.ACCOUNT_NOT_FOUND, cause);
    }

    /**
     * Wyjątek w przypadku niewłaściwej daty.
     *
     * @return wyjątek PrescriptionException
     */
    public static AppBaseException invalidDateException() {
        return new PrescriptionException(I18n.INVALID_DATE_PRESCRIPTION);
    }

    /**
     * Wyjątek w przypadku przedawnoinej recepty.
     *
     * @return wyjątek PrescriptionException
     */
    public static PrescriptionException prescriptionExpired() {
        return new PrescriptionException(I18n.PRESCRIPTION_EXPIRED);
    }

    /**
     * Wyjątek wystepujący, gdy wskazana recepta nie istnieje.
     *
     * @return wyjątek typu PrescriptionException
     */
    public static PrescriptionException noSuchPrescription() {
        return new PrescriptionException(I18n.NO_SUCH_PRESCRIPTION);
    }

    /**
     * Wyjątek występujący, gdy usuwanie recepty nie powiedzie się.
     *
     * @return wyjątek typu PrescriptionException
     */
    public static PrescriptionException prescriptionRemovalFailed() {
        return new PrescriptionException(I18n.PRESCRIPTION_REMOVAL_FAILED);
    }

    /**
     * Wyjątek występujący, gdy podjęta zostanie próba usunięcia recepty przed doktora,
     * który nie jest jej wystawcą.
     *
     * @return wyjątek typu PrescriptionException
     */
    public static PrescriptionException prescriptionRemovalUnauthorized() {
        return new PrescriptionException(I18n.PRESCRIPTION_REMOVAL_UNAUTHORIZED);
    }
}
