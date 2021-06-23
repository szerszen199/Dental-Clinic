package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;

/**
 * DTO dla przesyłania id dokumentacji która ma zostać usunięta.
 */
public class DeleteDocumentationEntryRequestDTO {
    @NotNull(message = I18n.DOCUMENTATION_ID_NULL)
    private Long id;


    /**
     * Tworzy nową instancję klasy Delete documentation entry request dto.
     */
    public DeleteDocumentationEntryRequestDTO() {
    }

    /**
     * Tworzy nową instancję klasy Delete documentation entry request dto.
     *
     * @param id id
     */
    public DeleteDocumentationEntryRequestDTO(Long id) {
        this.id = id;
    }

    /**
     * Pobiera pole id.
     *
     * @return id id
     */
    public Long getId() {
        return id;
    }

    /**
     * Ustawia pole id.
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }
}
