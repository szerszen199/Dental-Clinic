package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers.AppointmentManager;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.List;

@Startup
@Singleton
public class UnconfirmedAppointmentScheduler {

    private AppointmentManager appointmentManager;
    private MailProvider mailProvider;

    @Schedule(hour = "*", minute = "1", second = "1", info = "Every hour timer")
    public void remindAboutVisitConfirmation() throws AppointmentException {
        List<Appointment> appointments = appointmentManager.getScheduledAppointments();
    }

    @Schedule(hour = "*", minute = "1", second = "1", info = "Every hour timer")
    public void cancelUnconfirmedVisit(){

    }



}
