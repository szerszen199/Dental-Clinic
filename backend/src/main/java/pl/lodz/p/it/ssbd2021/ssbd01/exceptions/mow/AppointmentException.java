package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.APPOINTMENT_NOT_FOUND;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.APPOINTMENT_SLOT_REMOVAL_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.APPOINTMENT_WAS_BOOKED;

/**
 * Typ AppointmentException - wyjątek dla funkcjonalności związanych z wizytami.
 */
public class AppointmentException extends AppBaseException {
    
    private AppointmentException(String message) {
        super(message);
    }

    /**
     * Zwraca wyjątek z komunikatem {@link I18n#APPOINTMENT_NOT_FOUND}.
     *
     * @return {@link AppointmentException}
     */
    public static AppointmentException appointmentNotFound() {
        return new AppointmentException(APPOINTMENT_NOT_FOUND);
    }

    /**
     * Zwraca wyjątek z komunikatem {@link I18n#APPOINTMENT_SLOT_REMOVAL_FAILED}.
     *
     * @return {@link AppointmentException}
     */
    public static AppBaseException appointmentSlotRemovalFailed() {
        return new AppointmentException(APPOINTMENT_SLOT_REMOVAL_FAILED);
    }

    /**
     * Zwraca wyjątek z komunikatem {@link I18n#APPOINTMENT_WAS_BOOKED}.
     *
     * @return {@link AppointmentException}
     */
    public static AppBaseException appointmentWasBooked() {
        return new AppointmentException(APPOINTMENT_WAS_BOOKED);
    }

    public static AppointmentException getAllAppointmentsException() {
        return new AppointmentException(I18n.GET_ALL_APPOINTMENTS_FAILED);
    }

    public static AppointmentException getOwnAppointmentsException() {
        return new AppointmentException(I18n.GET_OWN_APPOINTMENTS_FAILED);
    }
}
