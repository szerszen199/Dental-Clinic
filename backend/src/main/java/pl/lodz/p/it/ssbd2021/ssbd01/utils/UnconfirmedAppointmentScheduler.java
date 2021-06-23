package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers.AppointmentManager;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.time.LocalDateTime;
import java.util.List;

@Startup
@Singleton
public class UnconfirmedAppointmentScheduler {

    private AppointmentManager appointmentManager;
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
            if (!appointment.getCanceled() && appointment.getPatient() != null && !appointment.getConfirmed() && appointment.getAppointmentDate().minusDays(3).isAfter(LocalDateTime.now())) {
                mailProvider.sendAppointmentConfirmationReminderMail(appointment.getPatient().getEmail(), appointment.getPatient().getLanguage());
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
            if (!appointment.getCanceled() && appointment.getPatient() != null && !appointment.getConfirmed() && appointment.getAppointmentDate().minusDays(1).isAfter(LocalDateTime.now())) {
                appointmentManager.cancelBookedAppointment(appointment.getId());
                //TODO: Upewnić się że działa poprawnie po implementacji metody w appointmentManagerze.
            }
        }

    }


}
