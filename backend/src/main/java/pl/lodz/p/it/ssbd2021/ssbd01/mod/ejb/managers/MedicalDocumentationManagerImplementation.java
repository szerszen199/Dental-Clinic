package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import org.apache.commons.lang3.NotImplementedException;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DocumentationEntry;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.MedicalDocumentation;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.DocumentationEntryFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.MedicalDocumentationFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.PrescriptionFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

/**
 * Klasa MedicalDocumentationManagerImplementation.
 */
@Stateful
@PermitAll
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class MedicalDocumentationManagerImplementation extends AbstractManager implements MedicalDocumentationManager {
    
    @Inject
    private DocumentationEntryFacade documentationEntryFacade;
    
    @Inject
    private MedicalDocumentationFacade documentationFacade;
    
    @Inject
    private PrescriptionFacade prescriptionFacade;
    
    @Inject
    private AccountFacade accountFacade;
    
    @Override
    public void addDocumentationEntry(Long patientId, DocumentationEntry entry) {
        throw new NotImplementedException();
    }

    @Override
    public void editDocumentationEntry(DocumentationEntry entry) {
        throw new NotImplementedException();
    }

    @Override
    public void removeDocumentationEntry(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public MedicalDocumentation getDocumentation(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public MedicalDocumentation getDocumentationByPatient(Long patientId) {
        throw new NotImplementedException();
    }

    @Override
    public void addPrescription(Long patientId, Prescription prescription) {
        throw new NotImplementedException();
    }

    @Override
    public void editPrescription(Prescription prescription) {
        throw new NotImplementedException();
    }

    @Override
    public void removePrescription(Long id) {
        throw new NotImplementedException();
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
