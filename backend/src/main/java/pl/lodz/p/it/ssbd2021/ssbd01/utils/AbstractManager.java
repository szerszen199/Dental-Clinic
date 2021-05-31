package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.BeforeCompletion;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstrakcyjna klasa po której powinne dziedziczyć inne managery, umożliwia logowanie.
 */
public abstract class AbstractManager {

    protected static final Logger LOGGER = Logger.getGlobal();
    @Inject
    LoggedInAccountUtil loggedInAccountUtil;
    boolean lastTransactionRollback;
    private String transactionID;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean isLastTransactionRollback() {
        return lastTransactionRollback;
    }

    /**
     * Metoda do logowania początku transakcji.
     */
    @AfterBegin
    public void afterBegin() {
        transactionID = Long.toString(System.currentTimeMillis()) + ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        LOGGER.log(Level.INFO, "Transakcja TXid = {0} rozpoczęta w {1}. dla użytkownika {2}", new Object[]{transactionID, this.getClass().getName(), loggedInAccountUtil.getLoggedInAccountLogin()});
    }

    /**
     * Metoda do logowania oczekiwania transakcji na zatwierdzenie.
     */
    @BeforeCompletion
    public void beforeCompletion() {
        LOGGER.log(Level.INFO, "Transakcja TXid={0} przed zatwierdzeniem w {1} dla użytkownika {2}.",
                new Object[]{transactionID, this.getClass().getName(), loggedInAccountUtil.getLoggedInAccountLogin()});
    }

    /**
     * Metoda do logowania końca transakcji.
     *
     * @param commmitted czy transakcja została zatwierdzona.
     */
    @AfterCompletion
    public void afterCompletion(boolean commmitted) {
        lastTransactionRollback = !commmitted;
        LOGGER.log(Level.INFO, "Transakcja TXid={0} zakończona w {1} poprzez {2} dla użytkownika {3}.",
                new Object[]{transactionID, this.getClass().getName(), commmitted ? "ZATWIERDZENIE" : "ODWOłANIE", loggedInAccountUtil.getLoggedInAccountLogin()});
    }
}
