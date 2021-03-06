package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import javax.ejb.ApplicationException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.APPOINTMENT_NOT_FOUND;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.APPOINTMENT_SLOT_REMOVAL_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.APPOINTMENT_WAS_BOOKED;


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
     *
     * @return wyjątek AppointmentException
     */
    public static AppointmentException appointmentNotDoctorOrInactive() {
        return new AppointmentException(I18n.APPOINTMENT_NOT_DOCTOR_OR_INACTIVE);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę działania na wizycie nie należącej do pacjenta.
     *
     * @return wyjątek typu AppointmentException reprezentujący próbę działania na wizycie nie należącej do pacjenta.
     */
    public static AppointmentException appointmentNotBelongingToPatient() {
        return new AppointmentException(I18n.APPOINTMENT_NOT_BELONGING_TO_PATIENT);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę potwierdzenia już potwierdzonej wizyty.
     *
     * @return zwraca wyjątek reprezentujący próbę potwierdzenia już potwierdzonej wizyty..
     */
    public static AppointmentException appointmentAlreadyConfirmed() {
        return new AppointmentException(I18n.APPOINTMENT_ALREADY_CONFIRMED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę potwierdzenia anulowanej wizyty.
     *
     * @return zwraca wyjątek reprezentujący próbę potwierdzenia anulowanej wizyty.
     */
    public static AppointmentException appointmentCanceled() {
        return new AppointmentException(I18n.APPOINTMENT_CANCELED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę ocenienia nieodbytej jeszcze wizyty.
     *
     * @return zwraca wyjątek reprezentujący próbę ocenienia nieodbytej jeszcze wizyty.
     */
    public static AppointmentException appointmentNotFinished() {
        return new AppointmentException(I18n.APPOINTMENT_NOT_FINISHED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę ocenienia niedozwoloną wartością.
     *
     * @return zwraca wyjątek reprezentujący próbę ocenienia niedozwoloną wartością.
     */
    public static AppointmentException invalidRatingScore() {
        return new AppointmentException(I18n.INVALID_RATING_SCORE);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę zatwierdzenia wizyty z przeszłości.
     *
     * @return zwraca wyjątek  reprezentujący próbę zatwierdzenia wizyty z przeszłości.
     */
    public static AppointmentException appointmentIsInPast() {
        return new AppointmentException(I18n.APPOINTMENT_IS_IN_PAST);
    }

    /**
     * Tworzy wyjątek reprezentujący nieprawidłowy token.
     *
     * @return zwraca wyjątek reprezentujący nieprawidłowy token.
     */
    public static AppointmentException invalidToken() {
        return new AppointmentException(I18n.INVALID_TOKEN);
    }

    /**
     * Tworzy wyjątek reprezentujący już ocenioną wizytę.
     *
     * @return zwraca wyjątek reprezentujący już ocenioną wizytę.
     */
    public static AppointmentException appointmentAlreadyRated() {
        return new AppointmentException(I18n.APPOINTMENT_ALREADY_RATED);
    }

    /**
     * Tworzy wyjątek reprezentujący błąd oceniania doktora.
     *
     * @return zwraca wyjątek reprezentujący błąd oceniania doktora.
     */
    public static AppointmentException doctorRatingFailed() {
        return new AppointmentException(I18n.DOCTOR_ALREADY_RATED);
    }
}
