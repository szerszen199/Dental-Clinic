package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

public class DoctorRatingException extends AppBaseException {
    
    private DoctorRatingException(String message) {
        super(message);
    }

    private DoctorRatingException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static DoctorRatingException getDoctorsAndRatesFailed() {
        return new DoctorRatingException(I18n.GET_DOCTORS_AND_RATES_FAILED);
    }
}
