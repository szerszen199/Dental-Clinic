package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

/**
 * Typ DoctorRatingException - dla listy lekarzy i ich ocen.
 */
public class DoctorRatingException extends AppBaseException {
    
    private DoctorRatingException(String message) {
        super(message);
    }

    /**
     * Pobiera listę lekarzy i ich ocen.
     *
     * @return {{@link DoctorRatingException}}
     */
    public static DoctorRatingException getDoctorsAndRatesFailed() {
        return new DoctorRatingException(I18n.GET_DOCTORS_AND_RATES_FAILED);
    }
}
