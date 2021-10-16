package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorRating;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

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
import java.util.List;

/**
 * Klasa definiująca operacje wykonywane na obiektach DoctorRating.
 */
@Stateless(name = "DoctorRatingFacadeMow")
@PermitAll
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LogInterceptor.class)
public class DoctorRatingFacade extends AbstractFacade<DoctorRating> {

    @PersistenceContext(unitName = "ssbd01mowPU")
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
     * @param entityClass typ obiektowy encji.
     */
    public DoctorRatingFacade(Class<DoctorRating> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Pobiera listę aktywnych lekarzy i statystyki ocen.
     *
     * @return lista aktywnych lekarzy i statystyk ich ocen
     * @throws AppBaseException bazowy wyjątek aplikacyjny
     */
    public List<DoctorRating> getActiveDoctorsAndRates() throws AppBaseException {
        try {
            TypedQuery<DoctorRating> tq = em.createNamedQuery("DoctorRating.findActiveDoctors", DoctorRating.class);
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
