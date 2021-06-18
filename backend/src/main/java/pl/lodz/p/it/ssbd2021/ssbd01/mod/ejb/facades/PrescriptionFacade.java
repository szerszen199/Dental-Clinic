package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades;

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
import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.PrescriptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu Prescription.
 */
@Stateless
@PermitAll
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LogInterceptor.class)
public class PrescriptionFacade extends AbstractFacade<Prescription> {

    @PersistenceContext(unitName = "ssbd01modPU")
    private EntityManager em;

    /**
     * Tworzy nową instancję klasy PrescriptionFacade.
     */
    public PrescriptionFacade() {
        super(Prescription.class);
    }

    /**
     * Tworzy nową instancję klasy PrescriptionFacade.
     *
     * @param entityClass typ obiektowy encji
     */
    public PrescriptionFacade(Class<Prescription> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Pobiera receptę na podstawie jej identyfikatora biznesowego.
     *
     * @param businessId identyfikator biznesowy recepty
     * @return recepta
     * @throws AppBaseException bazowy wyjątek aplikacji
     */
    public Prescription findByBusinessId(String businessId) throws AppBaseException {
        try {
            TypedQuery<Prescription> tq = em.createNamedQuery("Prescription.findByBusinessId", Prescription.class);
            tq.setParameter("business", businessId);
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw PrescriptionException.noSuchPrescription();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }
}
