package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request;

import javax.validation.constraints.NotNull;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

/**
 * Typ DeletePrescriptionRequestDTO - do funkcjonalności usuwania recepty.
 */
public class DeletePrescriptionRequestDTO {

    @NotNull(message = I18n.PRESCRIPTION_ID_NULL)
    private Long id;

    /**
     * Tworzy nową instancję klasy DeletePrescriptionRequestDTO.
     */
    public DeletePrescriptionRequestDTO() {
    }

    /**
     * Tworzy nową instancję klasy DeletePrescriptionRequestDTO.
     *
     * @param id klucz główny wizyty
     */
    public DeletePrescriptionRequestDTO(Long id) {
        this.id = id;
    }

    /**
     * Pobiera pole id.
     *
     * @return klucz główny wizyty
     */
    public Long getId() {
        return id;
    }

    /**
     * Ustawia pole id.
     *
     * @param id klucz główny recepty
     */
    public void setBusinessId(Long id) {
        this.id = id;
    }
}
