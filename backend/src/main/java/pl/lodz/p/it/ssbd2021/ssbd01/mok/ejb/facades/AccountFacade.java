package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu Account.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LogInterceptor.class)
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "ssbd01mokPU")
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
     * Find by login account.
     *
     * @param login login
     * @return account
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public Account findByLogin(String login) throws AppBaseException {
        try {
            TypedQuery<Account> tq = em.createNamedQuery("Account.findByLogin", Account.class);
            tq.setParameter("login", login);
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw AccountException.noSuchAccount(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }

    }

    @Override
    public void edit(Account entity) throws AppBaseException {
        try {
            Account oldAcc = findByLogin(entity.getLogin());
            Account newAcc = new Account(oldAcc.getId(), entity.getLogin(), entity.getEmail(),
                    entity.getPassword(), entity.getFirstName(), entity.getLastName(), entity.getPhoneNumber(), entity.getPesel());
            super.edit(newAcc);
        } catch (OptimisticLockException e) {
            throw AppBaseException.optimisticLockError(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }


}
