package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CreatePrescriptionRequestDTO {
    @NotNull(message = I18n.PATIENT_ID_NULL)
    @Login
    private String patient;

    @NotNull(message = I18n.EXPIRATION_DATE_NULL)
    private LocalDateTime expiration;

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    @NotNull(message = I18n.MEDICATIONS_NULL)
    private String medications;

    public CreatePrescriptionRequestDTO() {
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }
}
