package pl.lodz.p.it.ssbd2021.ssbd01.common;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

/**
 * Klasa abstrakcyjna definiująca główne operacje wykonywane na encjach
 * poprzez zarządcę encji w kontekście utrwalania.
 *
 * @param <T> klasa encyjna
 */

public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    /**
     * Tworzy nową instancję klasy AbstractFacade.
     *
     * @param entityClass typ obiektowy encji.
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    /**
     * Utrwala encję w bazie danych.
     *
     * @param entity obiekt encji.
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public void create(T entity) throws AppBaseException {
        try {
            getEntityManager().persist(entity);
        } catch (OptimisticLockException e) {
            throw AppBaseException.optimisticLockError(e);
        }
    }

    /**
     * Aktualizuje dane encji w bazie danych.
     *
     * @param entity obiekt encji.
     * @throws AppBaseException bazowy wyjątek aplikacji
     */
    @PermitAll
    public void edit(T entity) throws AppBaseException {
        try {
            getEntityManager().merge(entity);
            getEntityManager().flush();
        } catch (OptimisticLockException e) {
            throw AppBaseException.optimisticLockError(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    /**
     * Usuwa encję z bazy danych.
     *
     * @param entity obiekt encji.
     * @throws AppBaseException - wyjątek typu AppBaseException
     */
    public void remove(T entity) throws AppBaseException {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
        } catch (IllegalArgumentException e) {
            throw AppBaseException.mismatchedPersistenceArguments(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    /**
     * Szuka encji w bazie danych na podstawie obiektu klucza głównego.
     *
     * @param id obiekt klucza głównego.
     * @return obiekt encji.
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public T find(Object id) throws AppBaseException {
        try {
            return getEntityManager().find(entityClass, id);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    /**
     * Pobiera listę wszystkich encji znajdujących się w bazie danych.
     *
     * @return lista wszystkich encji.
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public List<T> findAll() throws AppBaseException {
        try {
            CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            return getEntityManager().createQuery(cq).getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }

    }

    /**
     * Pobiera listę encji danego typu z przedziału [range[0], range[1]].
     *
     * @param range przedział encji.
     * @return lista wybranych encji.
     */
    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Pobiera liczbę wszystkich encji danego typu znajdujących się w bazie danych.
     *
     * @return liczba wszystkich encji danego typu.
     */
    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
