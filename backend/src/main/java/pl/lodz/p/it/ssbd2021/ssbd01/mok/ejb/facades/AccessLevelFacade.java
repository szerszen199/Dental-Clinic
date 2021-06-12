package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu AccessLevel.
 */
@Stateless
@PermitAll
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LogInterceptor.class)
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Znajduje poziom dostępu konta o loginie {@param login} na podstawie nazwy
     * poziomu dostępu.
     *
     * @param login login użytkownika
     * @param level szukany poziom dostępu
     * @return zadany poziom dostępu dla zadanego użytkownika
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public AccessLevel findByAccountLoginAndAccessLevel(String login, String level) throws AppBaseException {
        TypedQuery<AccessLevel> tq = em.createNamedQuery("AccessLevel.findByAccountLoginAndAccessLevel", AccessLevel.class);
        tq.setParameter("accountLogin", login);
        tq.setParameter("level", level);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw AccountException.noSuchAccount(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    /**
     * Znajduje poziom dostępu konta o loginie {@param login} na podstawie nazwy
     * poziomu dostępu.
     *
     * @return zadany poziom dostępu dla zadanego użytkownika
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public List<AccessLevel> findByAccountId(Long id) throws AppBaseException {
        TypedQuery<AccessLevel> tq = em.createNamedQuery("AccessLevel.findByAccountId", AccessLevel.class);
        tq.setParameter("accountId", id);
        try {
            return  tq.getResultList();
        } catch (NoResultException e) {
            throw AccountException.noSuchAccount(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    /**
     * Znajduje poziom dostępu konta o kluczu głównym {@param login} na podstawie nazwy
     * poziomu dostępu.
     *
     * @param id    id użytkownika
     * @param level szukany poziom dostępu
     * @return zadany poziom dostępu dla zadanego użytkownika
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public AccessLevel findByAccountIdAndAccessLevel(Long id, String level) throws AppBaseException {
        TypedQuery<AccessLevel> tq = em.createNamedQuery("AccessLevel.findByAccountIdAndAccessLevel", AccessLevel.class);
        tq.setParameter("accountId", id);
        tq.setParameter("level", level);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw AccountException.noSuchAccount(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }
}
