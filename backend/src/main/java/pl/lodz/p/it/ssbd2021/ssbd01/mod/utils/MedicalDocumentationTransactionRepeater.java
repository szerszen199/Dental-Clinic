package pl.lodz.p.it.ssbd2021.ssbd01.mod.utils;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers.MedicalDocumentationManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Typ Medical documentation transaction repeater.
 */
@Stateless
public class MedicalDocumentationTransactionRepeater {

    @Inject
    private PropertiesLoader propertiesLoader;

    /**
     * Powtórzenie transakcji.
     *
     * @param repeatable                  implementacja interfejsu {@link Repeatable}
     * @param medicalDocumentationManager medical documentation manager
     * @throws Exception exception w przypadku niepowodzenia
     */
    public void repeatTransaction(Repeatable repeatable, MedicalDocumentationManager medicalDocumentationManager) throws Exception {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                repeatable.repeat();
                rollbackTX = medicalDocumentationManager.isLastTransactionRollback();
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
