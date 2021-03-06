package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.EncryptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.PrescriptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.CreatePrescriptionRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.EditPrescriptionRequestDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.PrescriptionResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.PrescriptionFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.Encryptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.IpAddressUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.annotation.security.RolesAllowed;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        if (createPrescriptionRequestDTO.getExpiration().isBefore(LocalDateTime.now())) {
            throw PrescriptionException.invalidDateException();
        }
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

    @RolesAllowed({I18n.DOCTOR})
    @Override
    public void editPrescription(EditPrescriptionRequestDto editPrescriptionRequestDto) throws PrescriptionException, EncryptionException {
        Prescription prescription;
        try {
            prescription = prescriptionFacade.find(editPrescriptionRequestDto.getId());
        } catch (AppBaseException e) {
            throw PrescriptionException.prescriptionNotFound();
        }

        if (prescription.getExpiration().isBefore(LocalDateTime.now())) {
            throw PrescriptionException.prescriptionExpired();
        }

        if (!editPrescriptionRequestDto.getVersion().equals(prescription.getVersion())) {
            throw PrescriptionException.versionMismatch();
        }
        Encryptor encryptor = new Encryptor(propertiesLoader);

        try {
            if (editPrescriptionRequestDto.getMedications() != null) {
                prescription.setMedications(encryptor.encryptMessage(editPrescriptionRequestDto.getMedications()));
            }
        } catch (Exception e) {
            throw EncryptionException.encryptingFailed();
        }

        try {
            prescription.setModifiedBy(accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
            prescription.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(request));
        } catch (AccountException e) {
            throw PrescriptionException.accountNotFound(e.getCause());
        } catch (Exception e) {
            throw PrescriptionException.prescriptionEditFailed();
        }

        try {
            prescriptionFacade.edit(prescription);
        } catch (AppBaseException e) {
            throw PrescriptionException.prescriptionEditFailed();
        }
    }

    @Override
    public Prescription findById(Long id) throws PrescriptionException {
        Prescription prescription;
        try {
            prescription = prescriptionFacade.find(id);
        } catch (AppBaseException e) {
            throw PrescriptionException.prescriptionNotFound();
        }
        return prescription;
    }

    @Override
    public List<PrescriptionResponseDto> getPatientPrescriptions() throws AppBaseException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Account account;
        account = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        List<Prescription> prescriptions = prescriptionFacade.findByPatientLogin(account.getLogin());
        List<PrescriptionResponseDto> prescriptionResponseDtoList = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            PrescriptionResponseDto prescriptionResponseDto = new PrescriptionResponseDto(
                    prescription.getId(),
                    prescription.getExpiration(),
                    prescription.getPatient().getFirstName(),
                    prescription.getPatient().getLastName(),
                    prescription.getDoctor().getLogin(),
                    prescription.getDoctor().getFirstName(),
                    prescription.getDoctor().getLastName(),
                    prescription.getCreationDateTime(),
                    prescription.getMedicationsDecrypted(propertiesLoader));
            prescriptionResponseDtoList.add(prescriptionResponseDto);
        }
        return prescriptionResponseDtoList;
    }

    @Override
    public List<PrescriptionResponseDto> getDoctorPrescriptions(String username) throws AppBaseException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Account account;
        account = accountFacade.findByLogin(username);
        List<Prescription> prescriptions = prescriptionFacade.findByPatientLogin(account.getLogin());
        List<PrescriptionResponseDto> prescriptionResponseDtoList = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            PrescriptionResponseDto prescriptionResponseDto = new PrescriptionResponseDto(
                    prescription.getId(),
                    prescription.getExpiration(),
                    prescription.getPatient().getFirstName(),
                    prescription.getPatient().getLastName(),
                    prescription.getDoctor().getLogin(),
                    prescription.getDoctor().getFirstName(),
                    prescription.getDoctor().getLastName(),
                    prescription.getCreationDateTime(),
                    prescription.getMedicationsDecrypted(propertiesLoader));
            prescriptionResponseDtoList.add(prescriptionResponseDto);
        }
        return prescriptionResponseDtoList;
    }

    @Override
    @RolesAllowed({I18n.DOCTOR})
    public void deletePrescription(Long id) throws AppBaseException {
        Account loggedInDoctor;
        Prescription prescription;

        try {
            loggedInDoctor = accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin());
        } catch (AccountException e) {
            throw PrescriptionException.prescriptionRemovalUnauthorized();
        } catch (AppBaseException e) {
            throw PrescriptionException.prescriptionRemovalFailed();
        }

        prescription = this.findById(id);

        if (prescription.getDoctor() != loggedInDoctor) {
            throw PrescriptionException.prescriptionRemovalUnauthorized();
        }

        try {
            prescriptionFacade.remove(prescription);
        } catch (Exception e) {
            throw PrescriptionException.prescriptionRemovalFailed();
        }
    }
}
