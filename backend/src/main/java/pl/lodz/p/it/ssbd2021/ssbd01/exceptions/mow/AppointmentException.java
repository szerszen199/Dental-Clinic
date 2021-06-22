package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.ejb.ApplicationException;


/**
 * Typ Appointment exception - wyjątki dla wizyty.
 */
@ApplicationException(rollback = true)
public class AppointmentException extends AppBaseException {

    /**
     * Tworzy nową instancję klasy wyjątku AppointmentException.
     *
     * @param message wiadomość zawarta w wyjątku
     */
    private AppointmentException(String message) {
        super(message);
    }

    /**
     * Tworzy nową instancję klasy wyjątku AppointmentException.
     *
     * @param message wiadomość zawarta w wyjątku
     * @param cause   przyczyna wystąpienia wyjątku
     */
    private AppointmentException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę utworzenia terminu wizyty, która się nie powiodła.
     *
     * @return wyjątek typu AppointmentException
     */
    public static AppointmentException appointmentCreationFailed() {
        return new AppointmentException(I18n.APPOINTMENT_SLOT_CREATION_FAILED);
    }

    /**
     * Wyjątek braku znalezenia wizyty o danym ID.
     *
     * @return AppointmentException wyjątek
     */
    public static AppointmentException appointmentNotFound() {
        return new AppointmentException(I18n.APPOINTMENT_NOT_FOUND);
    }

    /**
     * Wyjatek różnych wartości wersji.
     *
     * @return wyjątek AppointmentException
     */
    public static AppointmentException versionMismatch() {
        return new AppointmentException(I18n.VERSION_MISMATCH);
    }

    /**
     * Wyjątek braku znalezenia konta o danym ID dla wizyty.
     *
     * @return wyjątek AppointmentException
     */
    public static AppointmentException accountNotFound() {
        return new AppointmentException(I18n.ACCOUNT_NOT_FOUND);
    }

    /**
     * Wyjątek błędu edycji wizyty.
     *
     * @return wyjątek AppointmentException
     */
    public static AppointmentException appointmentEditFailed() {
        return new AppointmentException(I18n.APPOINTMENT_SLOT_EDIT_FAILED);
    }

    /**
     * Wyjątek nieaktywnego konta dla wizyty, lub gdy konto nie jest pacjentem.
     * @return  wyjątek AppointmentException
     */
    public static AppointmentException appointmentNotDoctorOrInactive() {
        return new AppointmentException(I18n.APPOINTMENT_NOT_DOCTOR_OR_INACTIVE);
    }
}
