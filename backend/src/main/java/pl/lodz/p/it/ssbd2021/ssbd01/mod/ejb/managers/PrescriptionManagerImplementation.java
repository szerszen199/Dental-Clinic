package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import org.apache.commons.lang3.NotImplementedException;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.PrescriptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.PrescriptionFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

@Stateful
@PermitAll
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class PrescriptionManagerImplementation extends AbstractManager implements PrescriptionManager {

    @Inject
    private PrescriptionFacade prescriptionFacade;

    @Inject
    private PropertiesLoader propertiesLoader;

    @Inject
    private LoggedInAccountUtil loggedInAccountUtil;

    @Inject
    private AccountFacade accountFacade;

    @Override
    public void addPrescription(Long patientId, Prescription prescription) {
        throw new NotImplementedException();
    }

    @Override
    public void editPrescription(Prescription prescription) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed({I18n.DOCTOR})
    public void deletePrescription(String businessId) throws AppBaseException {
        Account loggedInDoctor;
        Prescription prescription;

        try {
            loggedInDoctor = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        } catch (AccountException e) {
            throw PrescriptionException.prescriptionRemovalUnauthorized();
        } catch (AppBaseException e) {
            throw PrescriptionException.prescriptionRemovalFailed();
        }

        try {
            prescription = prescriptionFacade.findByBusinessId(businessId);
        } catch (PrescriptionException e) {
            throw e;
        } catch (AppBaseException e) {
            throw PrescriptionException.prescriptionRemovalFailed();
        }

        if (prescription.getDoctor() != loggedInDoctor) {
            throw PrescriptionException.prescriptionRemovalUnauthorized();
        }

        try {
            prescriptionFacade.remove(prescription);
        } catch (Exception e) {
            throw PrescriptionException.prescriptionRemovalFailed();
        }
    }

    @Override
    public Prescription getPrescription(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        throw new NotImplementedException();
    }
}
