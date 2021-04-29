package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu AccessLevel.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccessLevelFacade extends AbstractFacade<AccessLevel> {

    @PersistenceContext(unitName = "ssbd01mokPU")
    private EntityManager em;

    /**
     * Tworzy nową instancję klasy AccessLevelFacade.
     */
    public AccessLevelFacade() {
        super(AccessLevel.class);
    }

    /**
     * Tworzy nową instancję klasy AccessLevelFacade.
     *
     * @param entityClass typ obiektowy encji.
     */
    public AccessLevelFacade(Class<AccessLevel> entityClass) {
        super(entityClass);
    }

    /**
     * Find by account login and access level access level - znalezienie poziomu dostępu dla użytkownika o {@param login}.
     *
     * @param login login użytkownika
     * @param level level szukany poziom dostępu
     * @return access level zadany poziom dostępu dla zadanego użytkownika
     */
    public AccessLevel findByAccountLoginAndAccessLevel(String login, String level) {
        TypedQuery<AccessLevel> tq = em.createNamedQuery("AccessLevel.findByAccountLoginAndAccessLevel", AccessLevel.class);
        tq.setParameter("accountLogin", login);
        tq.setParameter("level", level);
        try {
            return tq.getSingleResult();
        } catch (NoResultException noResultException) {
            // TODO: 19.04.2021 opakować w wyjątek zaimplementowany w projekcie
            return null;
        }
    }

    /**
     * Find by account id and access level access level - znalezienie poziomu dostępu dla użytkownika o  {@param id}.
     *
     * @param id    id użytkownika
     * @param level level szukany poziom dostępu
     * @return access level zadany poziom dostępu dla zadanego użytkownika
     */
    public AccessLevel findByAccountIdAndAccessLevel(Long id, String level) {
        TypedQuery<AccessLevel> tq = em.createNamedQuery("AccessLevel.findByAccountIdAndAccessLevel", AccessLevel.class);
        tq.setParameter("accountId", id);
        tq.setParameter("level", level);
        try {
            return tq.getSingleResult();
        } catch (NoResultException noResultException) {
            // TODO: 19.04.2021 opakować w wyjątek zaimplementowany w projekcie
            return null;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
