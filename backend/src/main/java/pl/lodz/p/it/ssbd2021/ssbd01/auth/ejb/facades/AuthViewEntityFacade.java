package pl.lodz.p.it.ssbd2021.ssbd01.auth.ejb.facades;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AuthViewEntity;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Auth view fasada encji implementująca abstrakcyjną fasadę.
 */

@Stateless
@PermitAll
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
     * @throws AppBaseException app base wyjątek
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
