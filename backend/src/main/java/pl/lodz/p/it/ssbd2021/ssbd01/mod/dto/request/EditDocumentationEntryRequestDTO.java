package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;

/**
 * Klasa DTO podczas edycji wpisu w dokumentacji medycznej.
 */
public class EditDocumentationEntryRequestDTO {

    @NotNull(message = I18n.VERSION_NULL)
    private final Long version;
    private final String wasDone;
    private final String toBeDone;

    /**
     * Tworzy nową instancję klasy EditDocumentationEntryRequestDTO.
     *
     * @param version  version
     * @param wasDone  was done
     * @param toBeDone to be done
     */
    public EditDocumentationEntryRequestDTO(Long version, String wasDone, String toBeDone) {
        this.version = version;
        this.wasDone = wasDone;
        this.toBeDone = toBeDone;
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
     * Pobiera pole co zostało zrobione.
     *
     * @return was done
     */
    public String getWasDone() {
        return wasDone;
    }

    /**
     * Pobiera pole co ma zostać zrobione.
     *
     * @return to be done
     */
    public String getToBeDone() {
        return toBeDone;
    }
}
