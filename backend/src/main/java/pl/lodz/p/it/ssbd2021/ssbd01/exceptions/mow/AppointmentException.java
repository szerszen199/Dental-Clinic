package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import javax.ejb.ApplicationException;


/**
 * Klasa wyjątku dla wizyty.
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
     * Wyjątek w przypadku niepowodzenia odnalezienia konta edytującego wizytę.
     *
     * @param cause powód wyjątku
     * @return wyjątek PrescriptionException
     */
    public static AppointmentException accountNotFound(Throwable cause) {
        return new AppointmentException(I18n.ACCOUNT_NOT_FOUND, cause);
    }

    /**
     * Wyjątek błędu edycji wizyty.
     *
     * @return wyjątek AppointmentException
     */
    public static AppointmentException appointmentEditFailed() {
        return new AppointmentException(I18n.APPOINTMENT_EDIT_FAILED);
    }

    /**
     * Wyjątek nieaktywnego konta dla wizyty, lub gdy konto nie jest pacjentem.
     *
     * @return wyjątek AppointmentException
     */
    public static AppointmentException appointmentNotPatientInactive() {
        return new AppointmentException(I18n.NOT_PATIENT_OR_INACTIVE);
    }

    /**
     * Wyjątek błędnego pobiernania wszystkich wizyt.
     *
     * @return wyjątek AppointmentException
     */
    public static AppointmentException getAllAppointmentsException() {
        return new AppointmentException(I18n.GET_ALL_APPOINTMENTS_FAILED);
    }

    /**
     * Wyjątek błędnego pobiernania wszystkich umówionych wizyt.
     *
     * @return wyjątek AppointmentException
     */
    public static AppointmentException getAllScheduledAppointmentsException() {
        return new AppointmentException(I18n.GET_ALL_SCHEDULED_APPOINTMENTS_FAILED);
    }

    /**
     * Wyjątek błędnego pobiernania własnych wizyt.
     *
     * @return wyjątek AppointmentException
     */
    public static AppointmentException getOwnAppointmentsException() {
        return new AppointmentException(I18n.GET_OWN_APPOINTMENTS_FAILED);
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
     * Wyjątek nieaktywnego konta dla wizyty, lub gdy konto nie jest pacjentem.
     * @return  wyjątek AppointmentException
     */
    public static AppointmentException appointmentNotDoctorOrInactive() {
        return new AppointmentException(I18n.APPOINTMENT_NOT_DOCTOR_OR_INACTIVE);
    }
}
