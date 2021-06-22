package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorRating;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
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
import java.util.Optional;

/**
 * Klasa definiująca operacje wykonywane na obiektach DoctorRating.
 */
@Stateless
@PermitAll
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LogInterceptor.class)
public class DoctorRatingFacade extends AbstractFacade<DoctorRating> {

    @PersistenceContext(unitName = "ssbd01mokPU")
    private EntityManager em;

    /**
     * Tworzy nową instancję klasy DoctorRatingFacade.
     */
    public DoctorRatingFacade() {
        super(DoctorRating.class);
    }

    /**
     * Tworzy nową instancję klasy DoctorRatingFacade.
     * 
     * @param entityClass typ obiektowy encji
     */
    public DoctorRatingFacade(Class<DoctorRating> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Znajduje rekord statystyk dotyczących oceny lekarza na podstawie jego loginu.
     *
     * @param login login lekarza
     * @return obiekt typu Optional: zawierający statystyki ocen lekarza, jeśli je posiada,
     *         lub pusty, kiedy ich nie posiada.
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public Optional<DoctorRating> findByDoctorLogin(String login) throws AppBaseException {
        try {
            TypedQuery<DoctorRating> tq = em.createNamedQuery("DoctorRating.findByDoctorLogin", DoctorRating.class);
            tq.setParameter("doctorLogin", login);
            return Optional.of(tq.getSingleResult());
        } catch (NoResultException | NullPointerException e) {
            return Optional.empty();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    /**
     * Wyszukuje obiekty typu DoctorRating na podstawie pola 'active'.
     *
     * @param active wartość pola 'active'
     * @return lista obiektów typu DoctorRating o podanej wartości pola 'active'
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public List<DoctorRating> findByActive(Boolean active) throws AppBaseException {
        try {
            TypedQuery<DoctorRating> tq = em.createNamedQuery("DoctorRating.findByActive", DoctorRating.class);
            tq.setParameter("active", active);
            return tq.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        } catch (IllegalArgumentException e) {
            throw AppBaseException.mismatchedPersistenceArguments(e);
        }
    }
}
