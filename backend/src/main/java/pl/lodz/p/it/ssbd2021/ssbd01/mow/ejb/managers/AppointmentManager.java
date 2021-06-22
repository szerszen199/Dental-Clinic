package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.DoctorRatingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.PatientException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.AppointmentEditRequestDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.AllScheduledAppointmentsResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.AppointmentSlotEditRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.CreateAppointmentSlotRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.DoctorAndRateResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.PatientResponseDTO;

import javax.ejb.Local;
import java.util.List;


/**
 * Interfejs menadżera wizyt.
 */
@Local
public interface AppointmentManager {

    /**
     * Dodaje rezerwację do podanej wizyty.
     *
     * @param appointmentId klucz główny wizyty
     * @param login         login pacjenta
     */
    void bookAppointment(Long appointmentId, String login);

    /**
     * Anuluje umówioną wizytę.
     *
     * @param id klucz główny wizyty
     */
    void cancelBookedAppointment(Long id);

    /**
     * Modyfikuje slot wizyty.
     *
     * @param newAppointment edytowana wizyta
     * @throws AppointmentException wyjątek typu AppointmentException
     */
    void editAppointmentSlot(AppointmentSlotEditRequestDTO newAppointment) throws AppointmentException;


    /**
     * Pobiera wolne wizyty od teraz.
     *
     * @return wolne nadchodzące wizyty
     */
    List<Appointment> getAppointmentSlotsSinceNow();

    /**
     * Pobiera wszystkie umówione wizyty.
     *
     * @return umówione wizyty
     * @throws AppointmentException appointment exception
     */
    List<Appointment> getScheduledAppointments() throws AppointmentException;

    /**
     * Pobiera wszystkie umówione wizyty dla zalogowanego doktora.
     *
     * @return umówione wizyty
     * @throws AppointmentException appointment exception
     */
    List<Appointment> getScheduledAppointmentsByDoctor() throws AppointmentException;

    /**
     * Pobiera wszystkie umówione wizyty dla zalogowanego pacjenta.
     *
     * @return umówione wizyty
     * @throws AppointmentException appointment exception
     */
    List<Appointment> getScheduledAppointmentsByPatient() throws AppointmentException;

    /**
     * Pobiera wizytę o {@param id}.
     *
     * @param id klucz główny wizyty
     * @return umówione wizyty
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    Appointment findById(Long id) throws AppBaseException;

    /**
     * Dodaje ocenę lekarza po wizycie.
     *
     * @param doctorId klucz główny lekarza
     * @param rate     ocena
     */
    void rateAppointment(Long doctorId, Double rate) throws AppointmentException;

    /**
     * Pobiera wszystkich lekarzy i ich oceny.
     *
     * @return wszyscy lekarze i ich oceny
     * @throws DoctorRatingException wyjątek typu DoctorRatingException
     */
    List<DoctorAndRateResponseDTO> getAllDoctorsAndRates() throws DoctorRatingException;

    /**
     * Dodaje slot na wizytę.
     *
     * @param appointment wolna wizyta
     * @throws AccountException     wyjątek typu AccountException
     * @throws AppointmentException wyjątek typu AppointmentException
     */
    void addAppointmentSlot(CreateAppointmentSlotRequestDTO appointment) throws AppointmentException;

    /**
     * Modyfikuje umówioną wizyty.
     *
     * @param appointment wolna wizyta
     * @throws AppointmentException wyjątek appointmentException
     */
    void editBookedAppointment(AppointmentEditRequestDto appointment) throws AppointmentException;

    /**
     * Usuwa slot wizyty.
     *
     * @param id klucz główny wolnej wizyty
     */
    void removeAppointmentSlot(Long id);

    /**
     * Potwierdza własną umówioną wizytę.
     *
     * @param id klucz główny wizyty
     * @throws AppointmentException wyjątek sygnalizująvy błąd operacji na wizycie.
     * @throws MailSendingException wyjątek sygnalizująvy błąd wysyłania maila.
     */
    void confirmOwnBookedAppointment(Long id) throws AppointmentException, MailSendingException;

    /**
     * Potwierdza umówioną wizytę.
     *
     * @param id klucz główny wizyty
     * @throws AppointmentException wyjątek sygnalizująvy błąd operacji na wizycie.
     * @throws MailSendingException wyjątek sygnalizująvy błąd wysyłania maila.
     */
    void confirmBookedAppointment(Long id) throws AppointmentException, MailSendingException;

    /**
     * Pobiera wszystkich aktywnych pacjentów.
     *
     * @return lista wszystkich aktywnych pacjentów
     * @throws PatientException wyjątek typu PatientException
     */
    List<PatientResponseDTO> getActivePatients() throws PatientException;

    /**
     * Sprawdza czy ostatnia transakcja się powiodła.
     *
     * @return true jeśli ostatnia transakcja się nie powiodła, false jeśli nie.
     */
    boolean isLastTransactionRollback();

    /**
     * Pobiera wszystkie dostępne terminy wizyty.
     *
     * @return dostępne terminy wizyt.
     * @throws AppointmentException wyjątek.
     */
    List<Appointment> getAllAppointmentsSlots() throws AppointmentException;

    /**
     * Pobiera wszystkie dostępne terminy wizyty.
     *
     * @return dostępne terminy wizyt.
     * @throws AppointmentException wyjątek.
     */
    List<Appointment> getOwnAppointmentsSlots() throws AppointmentException;

    /**
     * Wysyła przypomnienie o konieczności potwierdzenia wizyty.
     *
     * @param id id wizyty dla której należy wysłać przypomnienie
     * @throws AppointmentException wyjątek sygnalizująvy błąd operacji na wizycie.
     * @throws MailSendingException wyjątek sygnalizująvy błąd wysyłania maila.
     */
    void sendAppointmentReminder(Long id) throws AppointmentException, MailSendingException;
}
