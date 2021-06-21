package pl.lodz.p.it.ssbd2021.ssbd01.mow.utils;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers.AppointmentManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Klasa odpowiedzialna za powtarzanie transakcji dla AppointmentManager.
 */
@Stateless
public class AppointmentTransactionRepeater {

    @Inject
    private PropertiesLoader propertiesLoader;

    /**
     * Powtórzenie transakcji.
     *
     * @param repeatable         implementacja interfejsu {@link AppointmentTransactionRepeater.Repeatable}
     * @param appointmentManager manager wizyt pacjentów
     * @throws Exception exception w przypadku niepowodzenia transakcji
     */
    public void repeatTransaction(AppointmentTransactionRepeater.Repeatable repeatable,
                                  AppointmentManager appointmentManager) throws Exception {

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
         * Powtórzenie transakcji.
         *
         * @throws AppBaseException wyjątek AppBaseException
         */
        void repeat() throws AppBaseException;
    }
}
