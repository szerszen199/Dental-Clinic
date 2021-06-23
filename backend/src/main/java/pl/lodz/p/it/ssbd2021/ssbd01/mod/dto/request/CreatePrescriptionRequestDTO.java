package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Typ Create prescription request dto.
 */
public class CreatePrescriptionRequestDTO {
    @NotNull(message = I18n.PATIENT_ID_NULL)
    @Login
    private String patient;

    @NotNull(message = I18n.EXPIRATION_DATE_NULL)
    private LocalDateTime expiration;
    @NotNull(message = I18n.MEDICATIONS_NULL)
    private String medications;

    /**
     * Tworzy nową instancję klasy Create prescription request dto.
     */
    public CreatePrescriptionRequestDTO() {
    }

    /**
     * Pobiera pole expiration.
     *
     * @return expiration
     */
    public LocalDateTime getExpiration() {
        return expiration;
    }

    /**
     * Ustawia pole expiration.
     *
     * @param expiration expiration
     */
    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    /**
     * Pobiera pole medications.
     *
     * @return medications
     */
    public String getMedications() {
        return medications;
    }

    /**
     * Ustawia pole medications.
     *
     * @param medications medications
     */
    public void setMedications(String medications) {
        this.medications = medications;
    }

    /**
     * Pobiera pole patient.
     *
     * @return patient
     */
    public String getPatient() {
        return patient;
    }

    /**
     * Ustawia pole patient.
     *
     * @param patient patient
     */
    public void setPatient(String patient) {
        this.patient = patient;
    }
}
