package pl.lodz.p.it.ssbd2021.ssbd01.common;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Klasa abstrakcyjna definiująca główne operacje wykonywane na encjach
 * poprzez zarządcę encji w kontekście utrwalania.
 *
 * @param <T> klasa encyjna
 */
@Stateless
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

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
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Aktualizuje dane encji w bazie danych.
     *
     * @param entity obiekt encji.
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Usuwa encję z bazy danych.
     *
     * @param entity obiekt encji.
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Szuka encji w bazie danych na podstawie obiektu klucza głównego.
     *
     * @param id obiekt klucza głównego.
     * @return obiekt encji.
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Pobiera listę wszystkich encji znajdujących się w bazie danych.
     *
     * @return lista wszystkich encji.
     */
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
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
