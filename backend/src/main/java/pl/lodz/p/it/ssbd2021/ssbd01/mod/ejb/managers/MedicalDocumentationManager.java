package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.DocumentationEntry;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.MedicalDocumentation;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs MedicalDocumentationManager.
 */
@Local
public interface MedicalDocumentationManager {

    /**
     * Dodaje wpis w dokumentacji medycznej pacjenta.
     *
     * @param patientId klucz główny pacjenta
     * @param entry     dodawany wpis
     */
    void addDocumentationEntry(Long patientId, DocumentationEntry entry);

    /**
     * Edytuje wpis w dokumentacji medycznej pacjenta.
     *
     * @param entry edytowany wpis
     */
    void editDocumentationEntry(DocumentationEntry entry);

    /**
     * Usuwa wpis z dokumentacji medycznej pacjenta.
     *
     * @param id klucz główny dokumentacji medycznej
     */
    void removeDocumentationEntry(Long id);

    /**
     * Pobiera dokumentację medyczną.
     *
     * @param id klucz główny dokumentacji medycznej
     * @return dokumentacja medyczna pacjenta
     */
    MedicalDocumentation getDocumentation(Long id);

    /**
     * Pobiera dokumentację medyczną dla pacjenta o danym kluczu głównym {@param patientId}.
     *
     * @param patientId klucz główny pacjenta
     * @return dokumentacja medyczna pacjenta
     */
    MedicalDocumentation getDocumentationByPatient(Long patientId);

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
     * @param id klucz główny recepty
     */
    void removePrescription(Long id);

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
}
