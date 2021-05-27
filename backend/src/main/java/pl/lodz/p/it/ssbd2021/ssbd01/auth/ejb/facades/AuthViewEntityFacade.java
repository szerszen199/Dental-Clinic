package pl.lodz.p.it.ssbd2021.ssbd01.auth.ejb.facades;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AuthViewEntity;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

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
 * Typ Auth view entity facade.
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LogInterceptor.class)
public class AuthViewEntityFacade extends AbstractFacade<AuthViewEntity> {

    @PersistenceContext(unitName = "ssbd01authPU")
    private EntityManager em;

    /**
     * Tworzy nową instancję klasy AbstractFacade.
     */
    public AuthViewEntityFacade() {
        super(AuthViewEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Pobranie listy kont z poziomami dostępu z widoku po loginie.
     *
     * @param login login
     * @return Lista kont
     * @throws AppBaseException app base exception
     */
    public List<AuthViewEntity> findByLogin(String login) throws AppBaseException {
        try {
            TypedQuery<AuthViewEntity> tq = em.createNamedQuery("AuthViewEntity.findByLogin", AuthViewEntity.class);
            tq.setParameter("login", login);
            return tq.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }
}
