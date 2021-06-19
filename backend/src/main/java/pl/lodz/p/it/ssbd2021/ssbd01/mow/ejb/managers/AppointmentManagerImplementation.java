package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers;

import org.apache.commons.lang3.NotImplementedException;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.BookAppointmentDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades.AppointmentFacade;
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
import java.util.List;
import java.util.Map;

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
    private LoggedInAccountUtil loggedInAccountUtil;

    @Override
    public void bookAppointment(BookAppointmentDto bookAppointmentDto) throws AppBaseException {
        Account account;
        try {
            account = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
        Appointment appointment = new Appointment();
        appointment.setCreatedBy(account);
        appointment.setCreatedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        appointment.setCanceled(false);
        appointment.setAppointmentDate(bookAppointmentDto.getAppointmentDate());
        appointment.setDoctor(bookAppointmentDto.getDoctor());
        appointment.setPatient(bookAppointmentDto.getPatient());
        appointmentFacade.create(appointment);

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
    public List<Account> getAllPatients() {
        throw new NotImplementedException();
    }
}
