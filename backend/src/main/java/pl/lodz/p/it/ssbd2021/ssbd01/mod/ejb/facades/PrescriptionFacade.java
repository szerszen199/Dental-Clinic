package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu Prescription.
 */
@Stateless
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
}
