package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.NotNull;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignableEntity;

/**
 * Klasa dto dla edycji recepty.
 */
public class EditPrescriptionRequestDto implements SignableEntity {

    @NotNull(message = I18n.PRESCRIPTION_ID_NULL)
    private Long id;

    @NotNull(message = I18n.PRESCRIPTION_MEDICATIONS_NULL)
    private String medications;

    @NotNull(message = I18n.VERSION_NULL)
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    @JsonbTransient
    public Map<String, String> getPayload() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("version", getVersion().toString());
        map.put("id", getId().toString());
        return map;
    }
}
