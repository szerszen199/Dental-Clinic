package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription;
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

    /**
     * Find by patient login list.
     *
     * @param patientLogin the patient login
     * @return the list
     * @throws AppBaseException the app base exception
     */
    public List<Prescription> findByPatientLogin(String patientLogin) throws AppBaseException {
        try {
            TypedQuery<Prescription> tq = em.createNamedQuery("Prescription.findByPatientLogin", Prescription.class);
            tq.setParameter("patientLogin", patientLogin);
            return tq.getResultList();
        } catch (NoResultException e) {
            throw AccountException.noSuchAccount(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    /**
     * Find by doctor login list.
     *
     * @param doctorLogin the doctor login
     * @return the list
     * @throws AppBaseException the app base exception
     */
    public List<Prescription> findByDoctorLogin(String doctorLogin) throws AppBaseException {
        try {
            TypedQuery<Prescription> tq = em.createNamedQuery("Prescription.findByDoctorLogin", Prescription.class);
            tq.setParameter("doctorLogin", doctorLogin);
            return tq.getResultList();
        } catch (NoResultException e) {
            throw AccountException.noSuchAccount(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
