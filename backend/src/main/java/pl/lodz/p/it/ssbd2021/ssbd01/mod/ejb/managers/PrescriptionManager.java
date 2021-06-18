package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs PrescriptionManager.
 */
@Local
public interface PrescriptionManager {

    /**
     * Dodaje receptę dla danego pacjenta.
     *
     * @param patientId    klucz główny pacjenta
     * @param prescription the prescription
     */
    void addPrescription(Long patientId, Prescription prescription);

    /**
     * Modyfikuje receptę.
     *
     * @param prescription recepta
     */
    void editPrescription(Prescription prescription);

    /**
     * Usuwa receptę.
     *
     * @param businessId identyfikator biznesowy recepty
     * @throws AppBaseException bazowy wyjątek aplikacji
     */
    void deletePrescription(String businessId) throws AppBaseException;

    /**
     * Pobiera receptę po zadanym kluczu głównym.
     *
     * @param id klucz główny recepty
     * @return recepta
     */
    Prescription getPrescription(Long id);

    /**
     * Pobiera wszystkie recepty dla danego pacjenta.
     *
     * @param patientId klucz główny pacjenta
     * @return lista recept pacjenta
     */
    List<Prescription> getPrescriptionsByPatient(Long patientId);

    /**
     * Sprawdza czy ostatnia transakcja się powiodła.
     *
     * @return true jeśli ostatnia transakcja się nie powiodła, false w przeciwnym wypadku.
     */
    boolean isLastTransactionRollback();
}
