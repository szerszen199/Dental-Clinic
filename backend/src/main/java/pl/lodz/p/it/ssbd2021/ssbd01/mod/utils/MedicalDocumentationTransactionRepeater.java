package pl.lodz.p.it.ssbd2021.ssbd01.mod.utils;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers.MedicalDocumentationManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Typ Medical documentation transaction repeater.
 */
@Stateless
public class MedicalDocumentationTransactionRepeater {

    @Inject
    private PropertiesLoader propertiesLoader;

    @Inject
    private MedicalDocumentationManager medicalDocumentationManager;

    /**
     * Powtórzenie transakcji.
     *
     * @param repeatable                  implementacja interfejsu {@link Repeatable}
     * @throws Exception exception w przypadku niepowodzenia
     */
    public void repeatTransaction(Repeatable repeatable) throws Exception {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                repeatable.repeat();
                rollbackTX = medicalDocumentationManager.isLastTransactionRollback();
            } catch (Exception e) {
                rollbackTX = true;
                exception = e;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            throw exception == null ? AppBaseException.transactionRepeatFailure() : exception;
        }
    }

}
