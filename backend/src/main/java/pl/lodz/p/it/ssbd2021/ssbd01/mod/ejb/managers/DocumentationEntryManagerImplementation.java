package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.DocumentationEntry;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.EncryptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.DocumentationEntryException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.EditDocumentationEntryRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades.DocumentationEntryFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.Encryptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

@Stateful
@PermitAll
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class DocumentationEntryManagerImplementation implements DocumentationEntryManager {

    @Inject
    private DocumentationEntryFacade documentationEntryFacade;

    @Inject
    private PropertiesLoader propertiesLoader;


    @Override
    public void editDocumentationEntry(EditDocumentationEntryRequestDTO editDocumentationEntryRequestDTO) throws EncryptionException, DocumentationEntryException {
        DocumentationEntry documentationEntry;
        try {
            documentationEntry = documentationEntryFacade.find(editDocumentationEntryRequestDTO.getId());
        } catch (AppBaseException e) {
            throw DocumentationEntryException.entryNotFoundError();
        }

        if (!editDocumentationEntryRequestDTO.getVersion().equals(documentationEntry.getVersion())) {
            throw DocumentationEntryException.versionMismatchException();
        }

        Encryptor encryptor = new Encryptor(propertiesLoader);

        try {
            if (editDocumentationEntryRequestDTO.getWasDone() != null) {
                documentationEntry.setWasDone(encryptor.encryptMessage(editDocumentationEntryRequestDTO.getWasDone()));
            }
            if (editDocumentationEntryRequestDTO.getToBeDone() != null) {
                documentationEntry.setWasDone(encryptor.encryptMessage(editDocumentationEntryRequestDTO.getToBeDone()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw EncryptionException.encryptingFailed();
        }

        try {
            documentationEntryFacade.edit(documentationEntry);
        } catch (Exception e) {
            throw DocumentationEntryException.documentationEntryEditionFailed();
        }
    }
}
