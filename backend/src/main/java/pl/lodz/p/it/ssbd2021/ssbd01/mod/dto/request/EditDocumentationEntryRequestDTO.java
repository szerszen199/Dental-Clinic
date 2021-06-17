package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request;

import java.util.HashMap;
import java.util.Map;
import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.NotNull;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignableEntity;

/**
 * Klasa DTO podczas edycji wpisu w dokumentacji medycznej.
 */
public class EditDocumentationEntryRequestDTO implements SignableEntity {

    @NotNull(message = I18n.VERSION_NULL)
    private Long version;
    private String wasDone;
    private String toBeDone;

    @NotNull(message = I18n.DOCUMENTATION_ENTRY_ID_NULL)
    private Long id;

    /**
     * Tworzy nową instancję klasy EditDocumentationEntryRequestDTO.
     *
     * @param version  version
     * @param wasDone  was done
     * @param toBeDone to be done
     * @param id       id
     */
    public EditDocumentationEntryRequestDTO(Long version, String wasDone, String toBeDone, Long id) {
        this.version = version;
        this.wasDone = wasDone;
        this.toBeDone = toBeDone;
        this.id = id;
    }

    /**
     * Tworzy nową instancję klasy EditDocumentationEntryRequestDTO.
     */
    public EditDocumentationEntryRequestDTO() {
    }

    /**
     * Pobiera wartość pola id.
     *
     * @return wartość pola id
     */
    public Long getId() {
        return id;
    }

    /**
     * Ustawia wartość pola id.
     *
     * @param id wartość pola id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Pobiera pole wersja.
     *
     * @return version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Ustawia wartość pola version.
     *
     * @param version wartość pola version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * Pobiera pole co zostało zrobione.
     *
     * @return was done
     */
    public String getWasDone() {
        return wasDone;
    }

    /**
     * Ustawia wartość pola wasDone.
     *
     * @param wasDone wartość pola wasDone
     */
    public void setWasDone(String wasDone) {
        this.wasDone = wasDone;
    }

    /**
     * Pobiera pole co ma zostać zrobione.
     *
     * @return to be done
     */
    public String getToBeDone() {
        return toBeDone;
    }

    /**
     * Ustawia wartość pola toBeDone.
     *
     * @param toBeDone wartość pola toBeDone
     */
    public void setToBeDone(String toBeDone) {
        this.toBeDone = toBeDone;
    }

    @Override
    @JsonbTransient
    public Map<String, String> getPayload() {
        Map<String, String> map = new HashMap<>();
        map.put("version", String.valueOf(getVersion()));
        return map;
    }
}
