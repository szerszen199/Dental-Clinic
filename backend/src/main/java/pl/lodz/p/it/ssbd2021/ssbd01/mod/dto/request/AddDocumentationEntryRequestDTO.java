package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.NotNull;

public class AddDocumentationEntryRequestDTO {
    @NotNull(message = I18n.PATIENT_ID_NULL)
    @Login
    private final String patient;

    private final String wasDone;

    private final String toBeDone;

    public AddDocumentationEntryRequestDTO(String patient, String wasDone, String toBeDone) {
        this.patient = patient;
        this.wasDone = wasDone;
        this.toBeDone = toBeDone;
    }

    public String getPatient() {
        return patient;
    }

    public String getWasDone() {
        return wasDone;
    }

    public String getToBeDone() {
        return toBeDone;
    }
}
