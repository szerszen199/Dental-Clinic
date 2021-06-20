package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.EncryptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.PrescriptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.CreatePrescriptionRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.EditPrescriptionRequestDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.PrescriptionResponseDto;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.Local;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Interfejs Prescriptions manager.
 */
@Local
public interface PrescriptionsManager {
    /**
     * Utworzenie recepty.
     *
     * @param createPrescriptionRequestDTO odpowiednie DTO.
     * @throws AppBaseException w przypadku wystąpienia błędów.
     */
    void createPrescription(CreatePrescriptionRequestDTO createPrescriptionRequestDTO) throws AppBaseException;

    /**
     * Sprawdza czy ostatnia transakcja się powiodła.
     *
     * @return true jeśli ostatnia transakcja się nie powiodła, false w przeciwnym wypadku.
     */
    boolean isLastTransactionRollback();


    /**
     * Edycja recepty.
     *
     * @param editPrescriptionRequestDto dto z danymi do edycji recepty
     * @throws PrescriptionException wyjątek PrescriptionException
     * @throws EncryptionException   wyjątek EncryptionException
     */
    void editPrescription(EditPrescriptionRequestDto editPrescriptionRequestDto) throws PrescriptionException, EncryptionException;

    /**
     * Pobiera receptę i {@param id}.
     *
     * @param id klucz główny wizyty
     * @return recepta
     * @throws PrescriptionException wyjątek typu PrescriptionException
     */
    Prescription findById(Long id) throws PrescriptionException;

    /**
     * Gets patient prescriptions.
     *
     * @return the patient prescriptions
     * @throws AppBaseException          the app base exception
     * @throws NoSuchPaddingException    the no such padding exception
     * @throws IllegalBlockSizeException the illegal block size exception
     * @throws NoSuchAlgorithmException  the no such algorithm exception
     * @throws BadPaddingException       the bad padding exception
     * @throws InvalidKeyException       the invalid key exception
     */
    List<PrescriptionResponseDto> getPatientPrescriptions() throws AppBaseException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    /**
     * Gets doctor prescriptions.
     *
     * @param username the username
     * @return the doctor prescriptions
     * @throws AppBaseException          the app base exception
     * @throws NoSuchPaddingException    the no such padding exception
     * @throws IllegalBlockSizeException the illegal block size exception
     * @throws NoSuchAlgorithmException  the no such algorithm exception
     * @throws BadPaddingException       the bad padding exception
     * @throws InvalidKeyException       the invalid key exception
     */
    List<PrescriptionResponseDto> getDoctorPrescriptions(String username) throws AppBaseException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

}
