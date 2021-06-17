package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

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
     * Modyfikuje umówioną wizytę.
     *
     * @param appointment modyfikowana wizyta
     */
    void editBookedAppointment(Appointment appointment);

    /**
     * Pobiera wszystkie wolne wizyty.
     *
     * @return wolne wizyty
     */
    List<Appointment> getAllAppointmentSlots();

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
     */
    List<Appointment> getBookedAppointments();

    /**
     * Dodaje ocenę lekarza po wizycie.
     *
     * @param doctorId klucz główny lekarza
     * @param rate     ocena
     */
    void rateDoctor(Long doctorId, Double rate);

    /**
     * Pobiera wszystkich lekarzy i ich oceny.
     *
     * @return wszyscy lekarze i ich oceny
     */
    Map<Account, Double> getAllDoctorsAndRates();

    /**
     * Dodaje slot na wizytę.
     *
     * @param appointment wolna wizyta
     */
    void addAppointmentSlot(Appointment appointment);

    /**
     * Modyfikuje slot wizyty.
     *
     * @param appointment wolna wizyta
     */
    void editAppointmentSlot(Appointment appointment);

    /**
     * Usuwa slot wizyty.
     *
     * @param id klucz główny wolnej wizyty
     * @throws AppBaseException bazowy wyjątek aplikacji.
     */
    void removeAppointmentSlot(Long id) throws AppBaseException;

    /**
     * Potwierdza umówioną wizytę.
     *
     * @param id klucz główny wizyty
     */
    void confirmBookedAppointment(Long id);

    /**
     * Pobiera wszystkich pacjentów.
     *
     * @return lista wszystkich pacjentów
     */
    List<Account> getAllPatients();

    /**
     * Sprawdza czy ostatnia transakcja się powiodła.
     *
     * @return true jeśli ostatnia transakcja się nie powiodła, false w przeciwnym wypadku.
     */
    boolean isLastTransactionRollback();
}
