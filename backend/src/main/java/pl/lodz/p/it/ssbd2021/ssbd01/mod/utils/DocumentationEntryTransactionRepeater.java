package pl.lodz.p.it.ssbd2021.ssbd01.mod.utils;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers.DocumentationEntryManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DocumentationEntryTransactionRepeater {

    @Inject
    private PropertiesLoader propertiesLoader;

    /**
     * Powtórzenie transakcji.
     *
     * @param repeatable                implementacja interfejsu {@link DocumentationEntryTransactionRepeater.Repeatable}
     * @param documentationEntryManager medical documentation manager
     * @throws Exception exception w przypadku niepowodzenia
     */
    public void repeatTransaction(DocumentationEntryTransactionRepeater.Repeatable repeatable, DocumentationEntryManager documentationEntryManager) throws Exception {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                repeatable.repeat();
                rollbackTX = documentationEntryManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            throw exception == null ? AppBaseException.transactionRepeatFailure() : exception;
        }
    }

    /**
     * Interfejs Repeatable.
     */
    @FunctionalInterface
    public interface Repeatable {
        /**
         * Metoda która ma zostać powtórzona.
         *
         * @throws AppBaseException w przypadku niepowodzenia
         */
        void repeat() throws AppBaseException;
    }

}
