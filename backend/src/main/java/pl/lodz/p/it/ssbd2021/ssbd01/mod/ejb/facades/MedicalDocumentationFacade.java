package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.MedicalDocumentation;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
