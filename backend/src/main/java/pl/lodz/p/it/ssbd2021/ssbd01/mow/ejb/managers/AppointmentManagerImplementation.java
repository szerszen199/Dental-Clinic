package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers;

import org.apache.commons.lang3.NotImplementedException;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorRating;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.DoctorRatingException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.DoctorAndRateResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AppointmentFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.DoctorRatingFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasa implementująca interfejs menadżera wizyt.
 */
@Stateful
@PermitAll
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class AppointmentManagerImplementation extends AbstractManager implements AppointmentManager {

    @Inject
    private AppointmentFacade appointmentFacade;

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private LoggedInAccountUtil loggedInAccountUtil;

    @Inject
    private DoctorRatingFacade doctorRatingFacade;

    @Override
    public void bookAppointment(Long appointmentId, String login) {
        throw new NotImplementedException();
    }

    @Override
    public void cancelBookedAppointment(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public void editBookedAppointment(Appointment appointment) {
        throw new NotImplementedException();
    }

    @Override
    public List<Appointment> getAllAppointmentSlots() {
        throw new NotImplementedException();
    }

    @Override
    public List<Appointment> getAppointmentSlotsSinceNow() {
        throw new NotImplementedException();
    }

    @Override
    public List<Appointment> getBookedAppointments() {
        throw new NotImplementedException();
    }

    @Override
    public void rateDoctor(Long doctorId, Double rate) {
        throw new NotImplementedException();
    }

    @Override
    public List<DoctorAndRateResponseDTO> getAllDoctorsAndRates() throws DoctorRatingException {
        try {
            List<DoctorRating> doctors = doctorRatingFacade.getActiveDoctorsAndRates();
            return doctors
                    .stream()
                    .map(doctor ->
                            new DoctorAndRateResponseDTO(doctor.getDoctor().getFirstName(),
                                    doctor.getDoctor().getLastName(),
                                    doctor.getAverage()))
                    .collect(Collectors.toList());
        } catch (AppBaseException e) {
            throw DoctorRatingException.getDoctorsAndRatesFailed();
        }
    }

    @Override
    public void addAppointmentSlot(Appointment appointment) {
        throw new NotImplementedException();
    }

    @Override
    public void editAppointmentSlot(Appointment appointment) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAppointmentSlot(Long id) throws AppBaseException {
        Appointment appointment;
        try {
            appointment = appointmentFacade.find(id);
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentNotFound();
        }
        
        if (appointment.getPatient() != null) {
            throw AppointmentException.appointmentWasBooked();
        }
        
        try {
            appointmentFacade.remove(appointment);
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentSlotRemovalFailed();
        }
    }

    @Override
    public void confirmBookedAppointment(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Account> getAllPatients() {
        throw new NotImplementedException();
    }

    @RolesAllowed({I18n.RECEPTIONIST, I18n.PATIENT})
    @Override
    public List<Appointment> getAllAppointmentsSlots() throws AppointmentException {
        try {
            return appointmentFacade.findAllFutureUnassignedAppointmentsSlots();
        } catch (AppBaseException e) {
            throw AppointmentException.getAllAppointmentsException();
        }


    }

    @RolesAllowed(I18n.DOCTOR)
    @Override
    public List<Appointment> getOwnAppointmentsSlots() throws AppointmentException {
        try {
            Account account = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
            return appointmentFacade.findFutureUnassignedAppointmentSlotsForDoctor(account.getId());
        } catch (AppBaseException e) {
            throw AppointmentException.getOwnAppointmentsException();
        }
    }
}
