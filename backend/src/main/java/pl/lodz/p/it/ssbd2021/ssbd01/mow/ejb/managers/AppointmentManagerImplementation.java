package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.NotImplementedException;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorRating;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.PrescriptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.DoctorRatingException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.AppointmentEditRequestDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.DoctorAndRateResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AppointmentFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.DoctorRatingFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;

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
    private DoctorRatingFacade doctorRatingFacade;

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
    public void editAppointmentSlot(AppointmentEditRequestDto appointmentEditRequestDto) throws AppointmentException {
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

        if (appointmentEditRequestDto.getAppointmentDate() != null) {
            appointment.setAppointmentDate(appointmentEditRequestDto.getAppointmentDate());
        }

        try {
            appointment.setModifiedBy(accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        } catch (AccountException e) {
            throw AppointmentException.accountNotFound(e.getCause());
        } catch (Exception e) {
            throw AppointmentException.appointmentEditFailed();
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
