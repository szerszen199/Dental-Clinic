package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers.AppointmentManager;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

@Startup
@Singleton
public class UnconfirmedAppointmentScheduler {

    @Inject
    private AppointmentManager appointmentManager;
    @Inject
    private MailProvider mailProvider;

    /**
     * Automatycznie wysyła przypomnienia o konieczności potwierdzenia wizyty.
     *
     * @throws AppointmentException błąd operacji na wizytach.
     * @throws MailSendingException błąd wysyłania wiadomości.
     */
    @Schedule(hour = "*", minute = "1", second = "1", info = "Every hour timer")
    public void remindAboutVisitConfirmation() throws AppointmentException, MailSendingException {
        List<Appointment> appointments = appointmentManager.getScheduledAppointments();
        for (Appointment appointment : appointments) {
            if (!appointment.getReminderMailSent() && !appointment.getCanceled() && appointment.getPatient()
                    != null && !appointment.getConfirmed() && appointment.getAppointmentDate().minusDays(3).isBefore(LocalDateTime.now())) {
                appointmentManager.sendAppointmentReminder(appointment.getId());
            }
        }
    }

    /**
     * Automatycznie anuluje niepotwierdzone wizyty.
     *
     * @throws AppointmentException błąd operacji na wizytach.
     * @throws MailSendingException błąd wysyłania wiadomości.
     */
    @Schedule(hour = "*", minute = "1", second = "1", info = "Every hour timer")
    public void cancelUnconfirmedVisit() throws AppointmentException {
        List<Appointment> appointments = appointmentManager.getScheduledAppointments();
        for (Appointment appointment : appointments) {
            if (!appointment.getCanceled() && appointment.getPatient() != null && !appointment.getConfirmed() && appointment.getAppointmentDate().minusDays(1).isBefore(LocalDateTime.now())) {
                appointmentManager.cancelBookedAppointmentScheduler(appointment.getId());
                //TODO: Upewnić się że działa poprawnie po implementacji metody w appointmentManagerze.
            }
        }

    }

    /**
     * Automatycznie wysyła link do potwierdzenia wizyty.
     *
     * @throws AppointmentException błąd operacji na wizytach.
     * @throws MailSendingException błąd wysyłania wiadomości.
     */
    @Schedule(hour = "*", minute = "1", second = "1", info = "Every hour timer")
    public void sendAppointmentRating() throws AppointmentException, MailSendingException {
        List<Appointment> appointments = appointmentManager.getScheduledAppointments();
        for (Appointment appointment : appointments) {
            if (!appointment.getRateMailSent() && !appointment.getCanceled() && appointment.getPatient()
                    != null && appointment.getConfirmed() && appointment.getAppointmentDate().isBefore(LocalDateTime.now())) {
                appointmentManager.sendAppointmentRateEmail(appointment.getId());
            }
        }
    }




}
