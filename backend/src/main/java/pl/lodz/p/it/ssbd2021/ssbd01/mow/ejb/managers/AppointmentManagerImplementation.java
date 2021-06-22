package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers;

import org.apache.commons.lang3.NotImplementedException;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorRating;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.DoctorRatingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.PatientException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.BookAppointmentDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.BookAppointmentSelfDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.DoctorAndRateResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.PatientResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AppointmentFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.DoctorRatingFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.IpAddressUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
    private HttpServletRequest request;
    @Inject
    private AppointmentFacade appointmentFacade;
    @Inject
    private AccountFacade accountFacade;
    @Inject
    private DoctorRatingFacade doctorRatingFacade;
    @Inject
    private LoggedInAccountUtil loggedInAccountUtil;

    @Override
    public void bookAppointmentSelf(BookAppointmentSelfDto bookAppointmentSelfDto) throws AppBaseException {
        Account account;
        Appointment appointment;
        try {
            account = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
            appointment = appointmentFacade.find(bookAppointmentSelfDto.getAppointmentId());
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
//        if (!editDocumentationEntryRequestDTO.getVersion().equals(documentationEntry.getVersion())) {
//            throw DocumentationEntryException.versionMismatchException();
//        }

        appointment.setPatient(account);
        appointment.setModifiedBy(account);
        appointment.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        appointment.setModificationDateTime(LocalDateTime.now());
        appointmentFacade.edit(appointment);
    }

    @Override
    public void bookAppointment(BookAppointmentDto bookAppointmentDto) throws AppBaseException {
        Account account;
        Account patient;
        Appointment appointment;
        try {
            account = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
            patient = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
            appointment = appointmentFacade.find(bookAppointmentDto.getAppointmentId());
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }

        appointment.setPatient(patient);
        appointment.setModifiedBy(account);
        appointment.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        appointment.setModificationDateTime(LocalDateTime.now());
        appointmentFacade.edit(appointment);
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
    public void removeAppointmentSlot(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public void confirmBookedAppointment(Long id) {
        throw new NotImplementedException();
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
}
