package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.NotNull;

/**
 * Klasa DTO podczas tworzenia wpisu w dokumentacji medycznej .
 */
public class AddDocumentationEntryRequestDTO {
    @NotNull(message = I18n.PATIENT_ID_NULL)
    @Login
    private String patient;
    private String wasDone;
    private String toBeDone;

    /**
     * Tworzy nową instancję klasy AddDocumentationEntryRequestDTO.
     *
     * @param patient  login pacjenta
     * @param wasDone  co zostało zrobione
     * @param toBeDone co ma zostać zrobione
     */
    public AddDocumentationEntryRequestDTO(String patient, String wasDone, String toBeDone) {
        this.patient = patient;
        this.wasDone = wasDone;
        this.toBeDone = toBeDone;
    }

    public AddDocumentationEntryRequestDTO() {
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getWasDone() {
        return wasDone;
    }

    public void setWasDone(String wasDone) {
        this.wasDone = wasDone;
    }

    public String getToBeDone() {
        return toBeDone;
    }

    public void setToBeDone(String toBeDone) {
        this.toBeDone = toBeDone;
    }
}
