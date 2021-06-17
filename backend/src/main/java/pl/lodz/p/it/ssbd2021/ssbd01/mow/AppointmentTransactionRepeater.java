package pl.lodz.p.it.ssbd2021.ssbd01.mow;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers.AppointmentManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

/**
 * Typ AppointmentTransactionRepeater - klasa realizująca mechanizm powtarzania transakcji.
 */
@Stateless
public class AppointmentTransactionRepeater {

    @Inject
    private PropertiesLoader propertiesLoader;

    /**
     * Powtarza transakcję w ramach komponentu AppointmentManager.
     *
     * @param repeatable         implementacja interfejsu {@link Repeatable}
     * @param appointmentManager komponent AppointmentManager
     * @throws Exception wyjątek w przypadku niepowodzenia
     */
    public void repeatTransaction(AppointmentTransactionRepeater.Repeatable repeatable, AppointmentManager appointmentManager) throws Exception {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                repeatable.repeat();
                rollbackTX = appointmentManager.isLastTransactionRollback();
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
