package pl.lodz.p.it.ssbd2021.ssbd01.mod.utils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers.PrescriptionManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

/**
 * Typ PrescriptionTransactionRepeater.
 */
@Stateless
public class PrescriptionTransactionRepeater {

    @Inject
    private PropertiesLoader propertiesLoader;

    /**
     * Powtarza określoną transakcję.
     *
     * @param repeatable          implementacja interfejsu {@link Repeatable}
     * @param prescriptionManager obiekt PrescriptionManager
     * @throws Exception wyjątek w przypadku niepowodzenia
     */
    public void repeatTransaction(Repeatable repeatable, PrescriptionManager prescriptionManager) throws Exception {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                repeatable.repeat();
                rollbackTX = prescriptionManager.isLastTransactionRollback();
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
