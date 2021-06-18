package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.PrescriptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.CreatePrescriptionRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.PrescriptionFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.IpAddressUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;

@Stateful
@RolesAllowed({I18n.DOCTOR, I18n.PATIENT})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class PrescriptionsManagerImplementation extends AbstractManager implements PrescriptionsManager {
    @Inject
    private PrescriptionFacade prescriptionFacade;

    @Inject
    private PropertiesLoader propertiesLoader;

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private HttpServletRequest request;

    @Inject
    private LoggedInAccountUtil loggedInAccountUtil;

    @RolesAllowed({I18n.DOCTOR})
    @Override
    public void createPrescription(CreatePrescriptionRequestDTO createPrescriptionRequestDTO) throws AppBaseException {
        Account doctor;
        Account patient;
        try {
            doctor = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
            patient = accountFacade.findByLogin(createPrescriptionRequestDTO.getPatient());
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
        Prescription prescription = new Prescription();
        try {
            prescription.setMedications(createPrescriptionRequestDTO.getMedications(), propertiesLoader);
        } catch (Exception e) {
            throw PrescriptionException.invalidMedicationsException();
        }
        prescription.setCreatedBy(doctor);
        prescription.setCreatedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setExpiration(createPrescriptionRequestDTO.getExpiration());
        try {
            prescriptionFacade.create(prescription);
        } catch (Exception e) {
            throw PrescriptionException.creationFailureException();
        }

    }
}
