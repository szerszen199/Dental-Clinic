package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.DoctorRatingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.PatientException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.AppointmentEditRequestDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.BookAppointmentDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.BookAppointmentSelfDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.AppointmentSlotEditRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.CreateAppointmentSlotRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.DoctorAndRateResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.PatientResponseDTO;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interfejs menadżera wizyt.
 */
@Local
public interface AppointmentManager {

    /**
     * Dodaje rezerwację do podanej wizyty.
     *
     * @param bookAppointmentSelfDto the book appointment self dto
     * @throws AppBaseException the app base exception
     */
    void bookAppointmentSelf(BookAppointmentSelfDto bookAppointmentSelfDto) throws AppBaseException;

    /**
     * Book appointment.
     *
     * @param bookAppointmentDto the book appointment dto
     * @throws AppBaseException the app base exception
     */
    void bookAppointment(BookAppointmentDto bookAppointmentDto) throws AppBaseException;

    /**
     * Anuluje umówioną wizytę.
     *
     * @param id klucz główny wizyty
     * @throws AppointmentException the appointment exception
     */
    void cancelBookedAppointment(Long id) throws AppointmentException;

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
     * Odwolanie wizyty przez scheduler.
     *
     * @param id the id
     * @throws AppointmentException the appointment exception
     */
    void cancelBookedAppointmentScheduler(Long id) throws AppointmentException;

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
     * @throws AppointmentException wyjątek typu AppointmentException
     */
    void rateAppointment(Long doctorId, BigDecimal rate) throws AppointmentException;

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
     * @throws AppBaseException bazowy wyjątek aplikacji.
     */
    void deleteAppointmentSlot(Long id) throws AppBaseException;

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
     * @throws AppointmentException wyjątek sygnalizujący błąd operacji na wizycie.
     * @throws MailSendingException wyjątek sygnalizujący błąd wysyłania maila.
     */
    void sendAppointmentReminder(Long id) throws AppointmentException, MailSendingException;

    /**
     * Wysyła mail z prośbą o ocenienie wizyty.
     *
     * @param id id wizyty dla której należy wysłać prośbę.
     * @throws AppointmentException wyjątek sygnalizujący błąd operacji na wizycie.
     * @throws MailSendingException wyjątek sygnalizujący błąd wysyłania maila.
     */
    void sendAppointmentRateEmail(Long id) throws AppointmentException, MailSendingException;

    /**
     * Sprawdza czy ostatnia transakcja się powiodła.
     *
     * @return true jeśli ostatnia transakcja się nie powiodła, false w przeciwnym wypadku.
     */
    boolean isLastTransactionRollback();

    /**
     * Odwołanie wizyty przez pacjenta.
     *
     * @param id the id
     * @throws AppointmentException the appointment exception
     */
    void cancelBookedAppointmentPatient(Long id) throws AppointmentException;

}
