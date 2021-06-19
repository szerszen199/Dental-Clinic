package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod;

import javax.ejb.ApplicationException;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

/**
 * Typ PrescriptionException - dla recept.
 */
@ApplicationException(rollback = true)
public class PrescriptionException extends AppBaseException {
    
    private PrescriptionException(String message) {
        super(message);
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
