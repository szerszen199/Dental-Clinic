package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers;

import javax.ejb.Local;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.MedicalDocumentation;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.EncryptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.DocumentationEntryException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.MedicalDocumentationException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.AddDocumentationEntryRequestDTO;

/**
 * Interfejs MedicalDocumentationManager.
 */
@Local
public interface MedicalDocumentationManager {

    /**
     * Tworzy dokumentację medyczną.
     *
     * @param login login użytkownika, dla którego tworzona jest dokumentacja
     * @throws MedicalDocumentationException wyjątek typu MedicalDocumentationException
     * @throws AccountException              wyjątek typu AccountException
     */
    void createMedicalDocumentation(String login) throws MedicalDocumentationException, AccountException;

    /**
     * Dodaje wpis w dokumentacji medycznej pacjenta.
     *
     * @param addDocumentationEntryRequestDTO DTO dla tworzenia wpisu dokumentacji
     * @throws DocumentationEntryException   wyjątek typu DocumentationEntryException
     * @throws AccountException              wyjątek typu AccountException
     * @throws EncryptionException           wyjątek typu EncryptionException
     * @throws MedicalDocumentationException wyjątek typu MedicalDocumentationException
     */
    void addDocumentationEntry(AddDocumentationEntryRequestDTO addDocumentationEntryRequestDTO)
            throws DocumentationEntryException, AccountException, EncryptionException, MedicalDocumentationException;


    /**
     * Usuwa wpis z dokumentacji medycznej pacjenta.
     *
     * @param id klucz główny dokumentacji medycznej
     * @throws AppBaseException wyjątek typu {@link AppBaseException} w przypadku niepowodzenia
     */
    void removeDocumentationEntry(Long id) throws AppBaseException;

    /**
     * Pobiera dokumentację medyczną.
     *
     * @param id klucz główny dokumentacji medycznej
     * @return dokumentacja medyczna pacjenta
     */
    MedicalDocumentation getDocumentation(Long id);

    /**
     * Pobiera dokumentację medyczną dla pacjenta o danym kluczu głównym {@param patientId}.
     * @param patientUsername login pacjenta
     * @return dokumentacja medyczna pacjenta
     * @throws MedicalDocumentationException przy błędach z pobraniem dokumentacji medycznej
     */
    MedicalDocumentation getDocumentationByPatient(String patientUsername) throws MedicalDocumentationException;

    /**
     * Sprawdza czy ostatnia transakcja się powiodła.
     *
     * @return true jeśli ostatnia transakcja się nie powiodła, false w przeciwnym wypadku.
     */
    boolean isLastTransactionRollback();
}
