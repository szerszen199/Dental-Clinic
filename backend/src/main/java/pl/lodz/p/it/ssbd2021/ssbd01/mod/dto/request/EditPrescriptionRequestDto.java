package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignableEntity;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa dto dla edycji recepty.
 */
public class EditPrescriptionRequestDto implements SignableEntity {

    @NotNull(message = I18n.PRESCRIPTION_ID_NULL)
    private String id;

    @NotNull(message = I18n.PRESCRIPTION_MEDICATIONS_NULL)
    private String medications;

    @NotNull(message = I18n.VERSION_NULL)
    private Long version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public Map<String, String> getPayload() {
        Map<String, String> map = new HashMap<>();
        map.put("version", String.valueOf(getVersion()));
        return map;
    }
}
