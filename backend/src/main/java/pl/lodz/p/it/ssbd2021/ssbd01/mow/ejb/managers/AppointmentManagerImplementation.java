package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers;

import java.util.List;
import java.util.Map;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.NotImplementedException;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AppointmentFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.IpAddressUtils;
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
    public void rateDoctor(Long doctorId, Double rate) {
        throw new NotImplementedException();
    }

    @Override
    public Map<Account, Double> getAllDoctorsAndRates() {
        throw new NotImplementedException();
    }

    @Override
    public void addAppointmentSlot(Appointment appointment) throws AppointmentException {
        String requestIp = IpAddressUtils.getClientIpAddressFromHttpServletRequest(request);
        appointment.setCreatedByIp(requestIp);

        try {
            appointmentFacade.create(appointment);
        } catch (Exception e) {
            throw AppointmentException.appointmentCreationFailed();
        }


//        try {
//            appointmentFacade.findByLoginOrEmailOrPesel(account.getLogin(), account.getEmail(), account.getPesel());
//        } catch (AccountException accountException) {
//            account.setCreatedBy(account);
//            try {
//                accountFacade.create(account);
//            } catch (Exception e) {
//                throw AccountException.accountCreationFailed();
//            }
//            try {
//                mailProvider.sendActivationMail(
//                        account.getEmail(),
//                        jwtRegistrationConfirmationUtils.generateJwtTokenForUsername(account.getLogin()), account.getLanguage()
//                );
//            } catch (Exception e) {
//                throw MailSendingException.activationLink();
//            }
//            return;
//        } catch (AppBaseException e) {
//            throw AccountException.accountCreationFailed();
//        }
//        throw AccountException.accountLoginEmailPeselExists();
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
    public List<Account> getAllPatients() {
        throw new NotImplementedException();
    }
}
