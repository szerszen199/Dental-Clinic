package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

public class AppointmentException extends AppBaseException {
    private AppointmentException(String message) {
        super(message);
    }

    private AppointmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AppointmentException getAllAppointmentsException(){
        return new AppointmentException(I18n.GET_ALL_APPOINTMENTS_FAILED);
    };

    public static AppointmentException getOwnAppointmentsException() {
        return new AppointmentException(I18n.GET_OWN_APPOINTMENTS_FAILED);
    }
}
