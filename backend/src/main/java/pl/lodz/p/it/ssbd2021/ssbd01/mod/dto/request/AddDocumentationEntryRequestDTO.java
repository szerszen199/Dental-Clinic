package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;

public class AddDocumentationEntryRequestDTO {
    @NotNull(message = I18n.DOCUMENTATION_ID_NULL)
    private final Long id;

    @NotNull(message = I18n.DOCTOR_ID_NULL)
    private final Long doctorId;

    private final String wasDone;

    private final String toBeDone;

    public AddDocumentationEntryRequestDTO(Long id, Long doctorId, String wasDone, String toBeDone) {
        this.id = id;
        this.doctorId = doctorId;
        this.wasDone = wasDone;
        this.toBeDone = toBeDone;
    }

    public Long getId() {
        return id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getWasDone() {
        return wasDone;
    }

    public String getToBeDone() {
        return toBeDone;
    }
}
