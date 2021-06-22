package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers;

import org.apache.commons.lang3.NotImplementedException;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorRating;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.DoctorRatingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.PatientException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.AppointmentEditRequestDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.AllScheduledAppointmentsResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.DoctorAndRateResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.PatientResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.ScheduledAppointmentResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AppointmentFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.DoctorRatingFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.IpAddressUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.MailProvider;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Inject
    private EntityIdentitySignerVerifier entityIdentitySignerVerifier;

    @Inject
    private MailProvider mailProvider;

    @Override
    public void bookAppointment(Long appointmentId, String login) {
        throw new NotImplementedException();
    }

    @Override
    public void cancelBookedAppointment(Long id)
    {

        throw new NotImplementedException();
    }

    @Override
    public void editAppointmentSlot(Appointment appointment) {
        throw new NotImplementedException();
    }

    @Override
    public List<Appointment> getAppointmentSlotsSinceNow() {
        throw new NotImplementedException();
    }

    @Override
    public AllScheduledAppointmentsResponseDTO getScheduledAppointments() throws AppointmentException {
        try {
            List<Appointment> appointments = appointmentFacade.findAllScheduledAppointments();
            List<ScheduledAppointmentResponseDTO> scheduledAppointmentResponseDTOS = new ArrayList<>();
            for (Appointment a : appointments) {
                scheduledAppointmentResponseDTOS.add(new ScheduledAppointmentResponseDTO(a, entityIdentitySignerVerifier));
            }
            return new AllScheduledAppointmentsResponseDTO(scheduledAppointmentResponseDTOS);
        } catch (AppBaseException e) {
            throw AppointmentException.getAllScheduledAppointmentsException();
        }
    }

    @Override
    public AllScheduledAppointmentsResponseDTO getScheduledAppointmentsByDoctor() throws AppointmentException {
        Account account;
        try {
            account = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        } catch (Exception e) {
            throw AppointmentException.accountNotFound();
        }
        try {
            List<Appointment> appointments = appointmentFacade.findAllScheduledAppointmentsByDoctor(account);
            List<ScheduledAppointmentResponseDTO> scheduledAppointmentResponseDTOS = new ArrayList<>();
            for (Appointment a : appointments) {
                scheduledAppointmentResponseDTOS.add(new ScheduledAppointmentResponseDTO(a, entityIdentitySignerVerifier));
            }
            return new AllScheduledAppointmentsResponseDTO(scheduledAppointmentResponseDTOS);
        } catch (AppBaseException e) {
            throw AppointmentException.getAllScheduledAppointmentsException();
        }
    }

    @Override
    public AllScheduledAppointmentsResponseDTO getScheduledAppointmentsByPatient() throws AppointmentException {
        Account account;
        try {
            account = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        } catch (Exception e) {
            throw AppointmentException.accountNotFound();
        }
        try {
            List<Appointment> appointments = appointmentFacade.findAllScheduledAppointmentsByPatient(account);
            List<ScheduledAppointmentResponseDTO> scheduledAppointmentResponseDTOS = new ArrayList<>();
            for (Appointment a : appointments) {
                scheduledAppointmentResponseDTOS.add(new ScheduledAppointmentResponseDTO(a, entityIdentitySignerVerifier));
            }
            return new AllScheduledAppointmentsResponseDTO(scheduledAppointmentResponseDTOS);
        } catch (AppBaseException e) {
            throw AppointmentException.getAllScheduledAppointmentsException();
        }
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
                            new DoctorAndRateResponseDTO(doctor.getDoctor().getLogin(),
                                    doctor.getDoctor().getFirstName(),
                                    doctor.getDoctor().getLastName(),
                                    doctor.getAverage()))
                    .collect(Collectors.toList());
        } catch (AppBaseException e) {
            throw DoctorRatingException.getDoctorsAndRatesFailed();
        }
    }

    @RolesAllowed({I18n.RECEPTIONIST})
    @Override
    public void addAppointmentSlot(Appointment appointment) throws AccountException, AppointmentException {
        Account account;
        try {
            account = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
        appointment.setCreatedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        appointment.setCreatedBy(account);
        try {
            appointmentFacade.create(appointment);
        } catch (Exception e) {
            throw AppointmentException.appointmentCreationFailed();
        }
    }

    @Override
    public void editBookedAppointment(AppointmentEditRequestDto appointmentEditRequestDto) throws AppointmentException {
        Appointment appointment;
        try {
            appointment = appointmentFacade.find(appointmentEditRequestDto.getId());
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentNotFound();
        }

        if (!appointmentEditRequestDto.getVersion().equals(appointment.getVersion())) {
            throw AppointmentException.versionMismatch();
        }


        try {
            Account account;
            if (appointmentEditRequestDto.getPatientLogin() != null) {
                account = accountFacade.findByLogin(appointmentEditRequestDto.getPatientLogin());
                final boolean isAccountActivePatient = account.getAccessLevels().stream()
                        .anyMatch(accessLevel -> accessLevel.getLevel().equals(I18n.PATIENT)
                                && accessLevel.getActive());
                if (account.getActive() && isAccountActivePatient) {
                    appointment.setPatient(account);
                } else {
                    throw AppointmentException.appointmentNotPatientInactive();
                }

            }
        } catch (AppBaseException e) {
            throw AppointmentException.accountNotFound();
        }
        try {
            appointment.setModifiedBy(accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        } catch (AccountException e) {
            throw AppointmentException.accountNotFound(e.getCause());
        } catch (Exception e) {
            throw AppointmentException.appointmentEditFailed();
        }

        if (appointmentEditRequestDto.getAppointmentDate() != null) {
            appointment.setAppointmentDate(appointmentEditRequestDto.getAppointmentDate());
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

    @RolesAllowed({I18n.PATIENT, I18n.RECEPTIONIST})
    @Override
    public void confirmBookedAppointment(Long id) throws AppointmentException,MailSendingException {
        Appointment appointment;
        String callerName = loggedInAccountUtil.getLoggedInAccountLogin();
        try {
            appointment = appointmentFacade.find(id);
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentNotFound();
        }
        if(appointment == null){
            throw AppointmentException.appointmentNotFound();
        }
        if (appointment.getPatient() == null || !appointment.getPatient().getLogin().equals(callerName)) {
            throw AppointmentException.appointmentNotBelongingToPatient();
        }
        if (appointment.getConfirmed()) {
            throw AppointmentException.appointmentAlreadyConfirmed();
        }
        if (appointment.getCanceled()) {
            throw AppointmentException.appointmentCanceled();
        }
        appointment.setConfirmed(true);
        try {
            appointmentFacade.edit(appointment);
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentEditFailed();
        }
        try {
            mailProvider.sendAppointmentConfirmedMail(appointment.getDoctor().getEmail(), appointment.getPatient().getLanguage());
            mailProvider.sendAppointmentConfirmedMail(appointment.getPatient().getEmail(), appointment.getPatient().getLanguage());
        } catch (MailSendingException e) {
            throw MailSendingException.mailFailed();
        }

    }

    @Override
    public List<PatientResponseDTO> getActivePatients() throws PatientException {
        try {
            List<Account> patients = accountFacade.getActivePatients();
            return patients
                    .stream()
                    .map(patient ->
                            new PatientResponseDTO(patient.getLogin(),
                                    patient.getEmail(),
                                    patient.getFirstName(),
                                    patient.getLastName(),
                                    patient.getPhoneNumber(),
                                    patient.getPesel()))
                    .collect(Collectors.toList());
        } catch (AppBaseException e) {
            throw PatientException.getActivePatients();
        }
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
