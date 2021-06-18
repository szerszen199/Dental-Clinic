package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class PrescriptionException extends AppBaseException {
    protected PrescriptionException(String message) {
        super(message);
    }

    protected PrescriptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public static PrescriptionException InvalidMedicationsException() {
        return new PrescriptionException(I18n.INVALID_MEDICATIONS);
    }

    public static PrescriptionException CreationFailureException() {
        return new PrescriptionException(I18n.PRESCRIPTION_CREATION_FAILURE);
    }
}
