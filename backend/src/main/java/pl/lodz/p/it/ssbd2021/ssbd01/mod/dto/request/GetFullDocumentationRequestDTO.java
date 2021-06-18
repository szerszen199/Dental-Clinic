package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.NotNull;

/**
 * Typ Get full documentation request dto.
 */
public class GetFullDocumentationRequestDTO {
    @NotNull(message = I18n.PATIENT_ID_NULL)
    @Login
    private String patient;

    /**
     * Tworzy nową instancję klasy Get full documentation request dto.
     *
     * @param patient patient
     */
    public GetFullDocumentationRequestDTO(String patient) {
        this.patient = patient;
    }

    /**
     * Tworzy nową instancję klasy Get full documentation request dto.
     */
    public GetFullDocumentationRequestDTO() {
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
