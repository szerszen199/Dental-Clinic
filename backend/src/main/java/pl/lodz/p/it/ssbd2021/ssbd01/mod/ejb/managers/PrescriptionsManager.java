package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.CreatePrescriptionRequestDTO;

import javax.ejb.Local;

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
}
