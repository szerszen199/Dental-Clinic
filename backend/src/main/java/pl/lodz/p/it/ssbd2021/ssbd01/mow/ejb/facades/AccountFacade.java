package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades;

import javax.annotation.security.PermitAll;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

import java.util.List;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu Account.
 */
@Stateless
@PermitAll
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LogInterceptor.class)
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "ssbd01mowPU")
    private EntityManager em;

    /**
     * Tworzy nową instancję klasy AccountFacade.
     */
    public AccountFacade() {
        super(Account.class);
    }

    /**
     * Tworzy nową instancję klasy AccountFacade.
     *
     * @param entityClass entity class
     */
    public AccountFacade(Class<Account> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Pobiera listę aktywnych pacjentów.
     *
     * @return lista aktywnych pacjentów
     * @throws AppBaseException bazowy wyjątek aplikacyjny
     */
    public List<Account> getActivePatients() throws AppBaseException {
        try {
            TypedQuery<Account> tq = em.createNamedQuery("Account.findActivePatients", Account.class);
            return tq.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        } catch (IllegalArgumentException e) {
            throw AppBaseException.mismatchedPersistenceArguments(e);
        } catch (EJBTransactionRolledbackException e) {
            throw AppBaseException.transactionRepeatFailure();
        }
    }
}
