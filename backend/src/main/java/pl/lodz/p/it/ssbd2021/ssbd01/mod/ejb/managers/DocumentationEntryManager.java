package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.EncryptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.DocumentationEntryException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.EditDocumentationEntryRequestDTO;

import javax.ejb.Local;

@Local
public interface DocumentationEntryManager {


    /**
     * Edytuje wpis w dokumentacji medycznej.
     *
     * @param editDocumentationEntryRequestDTO DTO z danymi do edycji
     * @throws AccountException            wyjątek AccountException
     * @throws EncryptionException         wyjątek EncryptionException
     * @throws DocumentationEntryException wyjątek DocumentationEntryException
     */
    void editDocumentationEntry(EditDocumentationEntryRequestDTO editDocumentationEntryRequestDTO) throws AccountException, EncryptionException, DocumentationEntryException;

    /**
     * Sprawdza czy ostatnia transakcja się powiodła.
     *
     * @return true jeśli ostatnia transakcja się nie powiodła, false w przeciwnym wypadku.
     */
    boolean isLastTransactionRollback();

}