package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.DocumentationEntry;
import pl.lodz.p.it.ssbd2021.ssbd01.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.Encryptor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.json.bind.annotation.JsonbTransient;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Typ Documentation entry response dto.
 */
public class DocumentationEntryResponseDTO implements SignableEntity {

    private final LocalDateTime creationTime;
    private final LocalDateTime modificationTime;
    private final String wasDone;
    private final String toBeDone;
    private final Long id;
    private final Long version;
    private final String doctorLogin;
    private final String etag;

    /**
     * Tworzy nową instancję klasy Documentation entry response dto.
     *
     * @param documentationEntry           wpis w dokumentacji
     * @param encryptor                    encryptor
     * @param entityIdentitySignerVerifier entity identity signer verifier
     * @throws NoSuchPaddingException    nie istniejący padding dla dekodowania
     * @throws IllegalBlockSizeException błędny rozmiar bloku dla dekodowania
     * @throws NoSuchAlgorithmException  błędny algorytm dla dekodowania
     * @throws BadPaddingException       błędny padding dla dekodowania
     * @throws InvalidKeyException       błędny klucz do dekodowania
     */
    // Podpis jest w dto i nie obchodzi mnie opinia hejterów
    //  Inaczej nie wyślę wszystkich
    public DocumentationEntryResponseDTO(
            DocumentationEntry documentationEntry,
            Encryptor encryptor,
            EntityIdentitySignerVerifier entityIdentitySignerVerifier)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        this.doctorLogin = documentationEntry.getDoctor().getLogin();
        this.version = documentationEntry.getVersion();
        this.id = documentationEntry.getId();
        this.wasDone = encryptor.decryptMessage(documentationEntry.getWasDone());
        this.toBeDone = encryptor.decryptMessage(documentationEntry.getToBeDone());
        this.creationTime = documentationEntry.getCreationDateTime();
        this.modificationTime = documentationEntry.getModificationDateTime();
        this.etag = entityIdentitySignerVerifier.sign(this);
    }

    /**
     * Pobiera pole creation time.
     *
     * @return creation time
     */
    public LocalDateTime getCreationTime() {
        return creationTime;
    }


    /**
     * Pobiera pole modification time.
     *
     * @return modification time
     */
    public LocalDateTime getModificationTime() {
        return modificationTime;
    }


    /**
     * Pobiera pole was done.
     *
     * @return was done
     */
    public String getWasDone() {
        return wasDone;
    }


    /**
     * Pobiera pole to be done.
     *
     * @return to be done
     */
    public String getToBeDone() {
        return toBeDone;
    }


    /**
     * Pobiera pole id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }


    /**
     * Pobiera pole doctor login.
     *
     * @return doctor login
     */
    public String getDoctorLogin() {
        return doctorLogin;
    }


    /**
     * Pobiera pole version.
     *
     * @return version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Pobiera pole etag.
     *
     * @return etag
     */
    public String getEtag() {
        return this.etag;
    }


    @Override
    @JsonbTransient
    public Map<String, String> getPayload() {
        Map<String, String> map = new HashMap<>();
        map.put("version", getVersion().toString());
        return map;
    }
}
