package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.MedicalDocumentation;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.MedicalDocumentationException;
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

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu MedicalDocumentation.
 */
@Stateless
@PermitAll
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LogInterceptor.class)
public class MedicalDocumentationFacade extends AbstractFacade<MedicalDocumentation> {

    @PersistenceContext(unitName = "ssbd01modPU")
    private EntityManager em;

    /**
     * Tworzy nową instancję klasy MedicalDocumentationFacade.
     */
    public MedicalDocumentationFacade() {
        super(MedicalDocumentation.class);
    }

    /**
     * Tworzy nową instancję klasy MedicalDocumentationFacade.
     *
     * @param entityClass typ obiektowy encji
     */
    public MedicalDocumentationFacade(Class<MedicalDocumentation> entityClass) {
        super(entityClass);
    }

    /**
     * Pobiera pole dokumentacja medyczna na podstawie loginu pacjenta.
     *
     * @param login login pacjenta
     * @return dokumentacja medyczna na podstawie loginu pacjenta
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public MedicalDocumentation getMedicalDocumentationByPatientLogin(String login) throws AppBaseException {
        try {
            TypedQuery<MedicalDocumentation> tq = em.createNamedQuery("MedicalDocumentation.findByPatientLogin", MedicalDocumentation.class);
            tq.setParameter("accountLogin", login);
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw MedicalDocumentationException.noSuchMedicalDocumentation(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
