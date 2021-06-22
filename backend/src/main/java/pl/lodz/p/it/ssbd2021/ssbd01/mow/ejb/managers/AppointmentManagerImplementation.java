package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.NotImplementedException;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorRating;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.DoctorRatingException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.AppointmentSlotEditRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.CreateAppointmentSlotRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.DoctorAndRateResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AppointmentFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.DoctorRatingFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.IpAddressUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;

/**
 * Klasa implementująca interfejs menadżera wizyt.
 */
@Stateful
@RolesAllowed({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.PATIENT})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class AppointmentManagerImplementation extends AbstractManager implements AppointmentManager {
    
    @Inject
    private AppointmentFacade appointmentFacade;
    
    @Inject
    private AccountFacade accountFacade;

    @Inject
    private DoctorRatingFacade doctorRatingFacade;

    @Inject
    private HttpServletRequest request;

    @Inject
    private LoggedInAccountUtil loggedInAccountUtil;

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
    public Appointment findById(Long id) throws AppBaseException {
        return appointmentFacade.find(id);
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

    @RolesAllowed({I18n.RECEPTIONIST})
    @Override
    public void addAppointmentSlot(CreateAppointmentSlotRequestDTO appointmentSlot) throws AppointmentException {
        Account doctor;
        boolean isAccountActiveDoctor;
        try {
            doctor = accountFacade.findByLogin(appointmentSlot.getDoctorLogin());
            isAccountActiveDoctor = doctor.getAccessLevels().stream()
                    .anyMatch(accessLevel -> accessLevel.getLevel().equals(I18n.DOCTOR)
                            && accessLevel.getActive());
        } catch (AppBaseException e) {
            throw AppointmentException.accountNotFound();
        }

        Account account;
        try {
            account = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        } catch (Exception e) {
            throw AppointmentException.accountNotFound();
        }

        Appointment appointment = new Appointment();

        if (doctor.getActive() && isAccountActiveDoctor) {
            appointment.setDoctor(doctor);
        } else {
            throw AppointmentException.appointmentNotDoctorOrInactive();
        }

        appointment.setCreatedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        appointment.setCreatedBy(account);
        appointment.setAppointmentDate(appointmentSlot.getAppointmentDate());

        try {
            appointmentFacade.create(appointment);
        } catch (Exception e) {
            throw AppointmentException.appointmentCreationFailed();
        }
    }

    @RolesAllowed({I18n.RECEPTIONIST})
    @Override
    public void editAppointmentSlot(AppointmentSlotEditRequestDTO newAppointment) throws AppointmentException {
        Appointment appointment;
        try {
            appointment = appointmentFacade.find(newAppointment.getId());
            if(appointment == null)
            {
                throw AppointmentException.appointmentNotFound();
            }
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentNotFound();
        }

        if (!newAppointment.getVersion().equals(appointment.getVersion())) {
            throw AppointmentException.versionMismatch();
        }
        if (newAppointment.getDoctorLogin() != null) {
            Account doctor;
            boolean isAccountActiveDoctor;
            try {
                doctor = accountFacade.findByLogin(newAppointment.getDoctorLogin());
                isAccountActiveDoctor = doctor.getAccessLevels().stream()
                        .anyMatch(accessLevel -> accessLevel.getLevel().equals(I18n.DOCTOR)
                                && accessLevel.getActive());
            } catch (AppBaseException e) {
                throw AppointmentException.accountNotFound();
            }

            try {
                if (doctor.getActive() && isAccountActiveDoctor) {
                    appointment.setDoctor(doctor);
                } else {
                    throw AppointmentException.appointmentNotDoctorOrInactive();
                }
            } catch (AppointmentException e) {
                throw AppointmentException.appointmentNotDoctorOrInactive();
            }
        }

        if (newAppointment.getAppointmentDate() != null) {
            appointment.setAppointmentDate(newAppointment.getAppointmentDate());
        }

        try {
            appointment.setModifiedBy(accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
            appointment.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        } catch (Exception e) {
            throw AppointmentException.accountNotFound();
        }

        try {
            appointmentFacade.edit(appointment);
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentEditFailed();
        }

    }

    @Override
    public void removeAppointmentSlot(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public void confirmBookedAppointment(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Account> getAllPatients() {
        throw new NotImplementedException();
    }
}
