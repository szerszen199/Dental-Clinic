package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Typ PrescriptionInfoDto.
 */
public class PrescriptionInfoDto implements SignableEntity {

    @NotNull(message = I18n.PRESCRIPTION_ID_NULL)
    private final Long id;

    @NotNull(message = I18n.PRESCRIPTION_MEDICATIONS_NULL)
    private final String medications;

    @NotNull(message = I18n.PRESCRIPTION_DATE_NULL)
    @Future(message = I18n.PRESCRIPTION_DATE_FUTURE)
    private final LocalDateTime expiration;

    @NotNull(message = I18n.VERSION_NULL)
    private final Long version;


    /**
     * Tworzy nową instancję klasy PrescriptionInfoDto.
     *
     * @param prescription     recepta
     * @param propertiesLoader propertiesLoader
     * @throws NoSuchPaddingException    nie istniejący padding dla dekodowania
     * @throws IllegalBlockSizeException błędny rozmiar bloku dla dekodowania
     * @throws NoSuchAlgorithmException  błędny algorytm dla dekodowania
     * @throws BadPaddingException       błędny padding dla dekodowania
     * @throws InvalidKeyException       błędny klucz do dekodowania
     */
    public PrescriptionInfoDto(Prescription prescription, PropertiesLoader propertiesLoader)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {
        this.id = prescription.getId();
        this.medications = prescription.getMedicationsDecrypted(propertiesLoader);
        this.expiration = prescription.getExpiration();
        this.version = prescription.getVersion();
    }


    public Long getId() {
        return id;
    }

    public String getMedications() {
        return medications;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public Long getVersion() {
        return version;
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
