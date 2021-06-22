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
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.AppointmentEditRequestDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.AppointmentSlotEditRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.CreateAppointmentSlotRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.DoctorAndRateResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.PatientResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AppointmentFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.DoctorRatingFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JWTAppointmentRatingUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.IpAddressUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.MailProvider;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
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

    @Inject
    JWTAppointmentRatingUtils jwtAppointmentRatingUtils;

    @Override
    public void bookAppointment(Long appointmentId, String login) {
        throw new NotImplementedException();
    }

    @Override
    public void cancelBookedAppointment(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Appointment> getAppointmentSlotsSinceNow() {
        throw new NotImplementedException();
    }

    @PermitAll
    @Override
    public List<Appointment> getScheduledAppointments() throws AppointmentException {
        try {
            return appointmentFacade.findAllScheduledAppointments();
        } catch (AppBaseException e) {
            throw AppointmentException.getAllScheduledAppointmentsException();
        }
    }

    @Override
    public List<Appointment> getScheduledAppointmentsByDoctor() throws AppointmentException {
        Account account;
        try {
            account = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        } catch (Exception e) {
            throw AppointmentException.accountNotFound();
        }
        try {
            return appointmentFacade.findAllScheduledAppointmentsByDoctor(account);
        } catch (AppBaseException e) {
            throw AppointmentException.getAllScheduledAppointmentsException();
        }
    }

    @Override
    public List<Appointment> getScheduledAppointmentsByPatient() throws AppointmentException {
        Account account;
        try {
            account = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        } catch (Exception e) {
            throw AppointmentException.accountNotFound();
        }
        try {
            return appointmentFacade.findAllScheduledAppointmentsByPatient(account);
        } catch (AppBaseException e) {
            throw AppointmentException.getAllScheduledAppointmentsException();
        }
    }

    @Override
    public Appointment findById(Long id) throws AppBaseException {
        Appointment appointment = appointmentFacade.find(id);
        ;
        if (appointment == null) {
            throw AppointmentException.appointmentNotFound();
        }
        return appointment;
    }

    @Override
    @PermitAll
    public void rateAppointment(String token, Long id, BigDecimal rate) throws AppointmentException {
        Appointment appointment;

        if (!jwtAppointmentRatingUtils.validateJwtToken(token)) {
            throw AppointmentException.invalidToken();
        }

        if (rate.doubleValue() < 0 || rate.doubleValue() > 5 || (rate.toString().length() != 3 && rate.toString().length() != 1)) {
            throw AppointmentException.invalidRatingScore();
        }
        try {
            appointment = appointmentFacade.find(id);
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentNotFound();
        }
        if (appointment == null) {
            throw AppointmentException.appointmentNotFound();
        }
        String callerName;
        try {
            callerName = jwtAppointmentRatingUtils.getUserNameFromJwtToken(token);
        } catch (ParseException e) {
            throw AppointmentException.accountNotFound();
        }

        if (appointment.getPatient() == null || !appointment.getPatient().getLogin().equals(callerName)) {
            throw AppointmentException.appointmentNotBelongingToPatient();
        }
        if (appointment.getCanceled()) {
            throw AppointmentException.appointmentCanceled();
        }
        if (appointment.getAppointmentDate().isAfter(LocalDateTime.now())) {
            throw AppointmentException.appointmentNotFinished();
        }
        if (appointment.getRating() != null) {
            throw AppointmentException.appointmentAlreadyRated();
        }
        try {
            appointmentFacade.updateRating(id, rate);
        } catch (AppointmentException e) {
            throw AppointmentException.appointmentEditFailed();
        }
        try {
            List<DoctorRating> doctorRatings = doctorRatingFacade.findAll();
            for (DoctorRating doctorRating : doctorRatings) {
                if (doctorRating.getDoctor().getLogin().equals(appointment.getDoctor().getLogin())) {
                    doctorRating.setRatesCounter(doctorRating.getRatesCounter() + 1);
                    doctorRating.setRatesSum(doctorRating.getRatesSum() + appointment.getRating().doubleValue());
                    doctorRatingFacade.edit(doctorRating);
                }
            }
        } catch (AppBaseException e) {
            throw AppointmentException.doctorRatingFailed();
        }
    }

    @Override
    @PermitAll
    public void sendAppointmentRateEmail(Long id) throws AppointmentException, MailSendingException {
        Appointment appointment;
        try {
            appointment = appointmentFacade.find(id);
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentNotFound();
        }
        if (appointment == null) {
            throw AppointmentException.appointmentNotFound();
        }
        if (appointment.getPatient() == null) {
            throw AppointmentException.appointmentNotBelongingToPatient();
        }

        try {
            mailProvider.sendAppointmentRateMail(appointment.getPatient().getEmail(), appointment.getPatient().getLanguage(),
                    jwtAppointmentRatingUtils.generateJwtTokenForUsername(appointment.getPatient().getLogin()), id);
        } catch (MailSendingException e) {
            throw MailSendingException.mailFailed();
        }
        appointment.setRateMailSent(true);
        try {
            appointmentFacade.edit(appointment);
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentEditFailed();
        }
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
                                    doctor.getAverage(),
                                    doctor.getRatesCounter()))
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
            if (appointment == null) {
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

    @RolesAllowed({I18n.PATIENT})
    @Override
    public void confirmOwnBookedAppointment(Long id) throws AppointmentException, MailSendingException {
        Appointment appointment;
        String callerName = loggedInAccountUtil.getLoggedInAccountLogin();
        try {
            appointment = appointmentFacade.find(id);
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentNotFound();
        }
        if (appointment == null) {
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

    @RolesAllowed({I18n.RECEPTIONIST})
    @Override
    public void confirmBookedAppointment(Long id) throws AppointmentException, MailSendingException {
        Appointment appointment;
        try {
            appointment = appointmentFacade.find(id);
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentNotFound();
        }
        if (appointment == null) {
            throw AppointmentException.appointmentNotFound();
        }
        if (appointment.getPatient() == null) {
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

    @PermitAll
    @Override
    public void sendAppointmentReminder(Long id) throws AppointmentException, MailSendingException {
        Appointment appointment;
        try {
            appointment = appointmentFacade.find(id);
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentNotFound();
        }
        mailProvider.sendAppointmentConfirmationReminderMail(appointment.getPatient().getEmail(), appointment.getPatient().getLanguage());
        appointment.setReminderMailSent(true);
        try {
            appointmentFacade.edit(appointment);
        } catch (AppBaseException e) {
            throw AppointmentException.appointmentEditFailed();
        }
    }
}
