package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.DocumentationEntry;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.MedicalDocumentation;
import pl.lodz.p.it.ssbd2021.ssbd01.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.Encryptor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Typ Documentation info response dto.
 */
public class DocumentationInfoResponseDTO {
    private final Long id;
    private final String patientUsername;
    private final List<DocumentationEntryResponseDTO> documentationEntries;

    /**
     * Tworzy nową instancję klasy Documentation info response dto.
     *
     * @param documentation                dokumentacja
     * @param encryptor                    encryptor
     * @param entityIdentitySignerVerifier entity identity signer verifier
     * @throws NoSuchPaddingException    nie istniejący padding dla dekodowania
     * @throws IllegalBlockSizeException błędny rozmiar bloku dla dekodowania
     * @throws NoSuchAlgorithmException  błędny algorytm dla dekodowania
     * @throws BadPaddingException       błędny padding dla dekodowania
     * @throws InvalidKeyException       błędny klucz do dekodowania
     */
    public DocumentationInfoResponseDTO(MedicalDocumentation documentation,
                                        List<DocumentationEntry> documentationEntryList,
                                        Encryptor encryptor,
                                        EntityIdentitySignerVerifier entityIdentitySignerVerifier)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        documentationEntries = new ArrayList<>();
        for (DocumentationEntry documentationEntry : documentationEntryList) {
            documentationEntries.add(new DocumentationEntryResponseDTO(documentationEntry, encryptor, entityIdentitySignerVerifier));
        }
        this.patientUsername = documentation.getPatient().getLogin();
        this.id = documentation.getId();
    }

    /**
     * Pobiera pole documentation entries.
     *
     * @return documentation entries
     */
    public List<DocumentationEntryResponseDTO> getDocumentationEntries() {
        return documentationEntries;
    }

    /**
     * Pobiera pole patient username.
     *
     * @return patient username
     */
    public String getPatientUsername() {
        return patientUsername;
    }

    /**
     * Pobiera pole id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

}
