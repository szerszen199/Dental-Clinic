package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers;

import org.apache.commons.lang3.NotImplementedException;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DocumentationEntry;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.MedicalDocumentation;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.EncryptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.DocumentationEntryException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.MedicalDocumentationException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.AddDocumentationEntryRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.DocumentationEntryFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.MedicalDocumentationFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.PrescriptionFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.Encryptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.IpAddressUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    private MedicalDocumentationFacade medicalDocumentationFacade;

    @Inject
    private HttpServletRequest request;

    @Inject
    private MedicalDocumentationFacade documentationFacade;

    @Inject
    private PrescriptionFacade prescriptionFacade;

    @Inject
    private PropertiesLoader propertiesLoader;

    @Inject
    private LoggedInAccountUtil loggedInAccountUtil;

    @Inject
    private AccountFacade accountFacade;


    @Override
    public void createMedicalDocumentation(String login) throws MedicalDocumentationException, AccountException {
        Account account;
        try {
            account = accountFacade.findByLogin(login);
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
        try {
            MedicalDocumentation medicalDocumentation = new MedicalDocumentation();
            medicalDocumentation.setCreatedBy(account);
            medicalDocumentation.setCreatedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));

            // TODO: 07.06.2021 Co z alegriami i lekarstwami?
            medicalDocumentation.setAllergies("");
            medicalDocumentation.setMedicationsTaken("");

            medicalDocumentation.setPatient(account);
            medicalDocumentationFacade.create(medicalDocumentation);
        } catch (Exception e) {
            throw MedicalDocumentationException.medicalDocumentationCreationFailed();
        }
    }

    @Override
    public void addDocumentationEntry(AddDocumentationEntryRequestDTO addDocumentationEntryRequestDTO)
            throws MedicalDocumentationException, AccountException, EncryptionException, DocumentationEntryException {
        Account doctor;
        MedicalDocumentation medicalDocumentation;
        try {
            doctor = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        } catch (Exception e) {
            throw AccountException.noSuchAccount(e);
        }
        try {
            medicalDocumentation = documentationFacade.getMedicalDocumentationByPatientLogin(addDocumentationEntryRequestDTO.getPatient());
        } catch (AppBaseException e) {
            throw MedicalDocumentationException.noSuchMedicalDocumentation(e);
        }
        Encryptor encryptor = new Encryptor(propertiesLoader);
        byte[] wasDoneEncrypted;
        byte[] toBeDoneEncrypted;
        try {
            wasDoneEncrypted = encryptor.encryptMessage(addDocumentationEntryRequestDTO.getWasDone());
            toBeDoneEncrypted = encryptor.encryptMessage(addDocumentationEntryRequestDTO.getToBeDone());
        } catch (Exception e) {
            e.printStackTrace();
            throw EncryptionException.encryptingFailed();
        }
        DocumentationEntry documentationEntry = new DocumentationEntry(doctor,
                wasDoneEncrypted,
                toBeDoneEncrypted,
                medicalDocumentation);
        String requestIp = IpAddressUtils.getClientIpAddressFromHttpServletRequest(request);
        documentationEntry.setCreatedByIp(requestIp);
        documentationEntry.setCreatedBy(doctor);
        try {
            documentationEntryFacade.create(documentationEntry);
        } catch (Exception e) {
            throw DocumentationEntryException.documentationEntryCreationFailed();
        }
    }

    @Override
    public void removeDocumentationEntry(Long id) throws AppBaseException {
        DocumentationEntry documentationEntry;
        Account loggedInDoctor;
        try {
            loggedInDoctor = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        } catch (Exception e) {
            // TODO: 07.06.2021 Coś musiało ostro pojsc nie tak, aplikacja wybucha
            throw e;
        }
        try {
            documentationEntry = documentationEntryFacade.find(id);
        } catch (AppBaseException e) {
            throw DocumentationEntryException.entryNotFoundError();
        }
        if (!documentationEntry.getDoctor().equals(loggedInDoctor) || documentationEntry.getMedicalDocumentation().getPatient().equals(loggedInDoctor)) {
            throw DocumentationEntryException.invalidDoctorException();
        }
        try {
            documentationEntryFacade.remove(documentationEntry);
        } catch (Exception e) {
            throw DocumentationEntryException.removalFailedError();
        }
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
