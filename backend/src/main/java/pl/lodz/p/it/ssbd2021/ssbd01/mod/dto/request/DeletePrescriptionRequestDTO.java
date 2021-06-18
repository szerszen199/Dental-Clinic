package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request;

import javax.validation.constraints.NotNull;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

/**
 * Typ DeletePrescriptionRequestDTO - do funkcjonalności usuwania recepty.
 */
public class DeletePrescriptionRequestDTO {

    @NotNull(message = I18n.PRESCRIPTION_ID_NULL)
    private String businessId;

    /**
     * Tworzy nową instancję klasy DeletePrescriptionRequestDTO.
     */
    public DeletePrescriptionRequestDTO() {
    }

    /**
     * Tworzy nową instancję klasy DeletePrescriptionRequestDTO.
     *
     * @param businessId the business id
     */
    public DeletePrescriptionRequestDTO(String businessId) {
        this.businessId = businessId;
    }

    /**
     * Pobiera pole businessId.
     *
     * @return identyfikator biznesowy recepty
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * Ustawia pole businessId.
     *
     * @param businessId identyfikator biznesowy recepty
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}
