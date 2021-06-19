package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

public class PatientException extends AppBaseException {

    private PatientException(String message) {
        super(message);
    }

    private PatientException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static PatientException getActivePatients() {
        return new PatientException(I18n.GET_ACTIVE_PATIENTS_FAILED);
    }
}
