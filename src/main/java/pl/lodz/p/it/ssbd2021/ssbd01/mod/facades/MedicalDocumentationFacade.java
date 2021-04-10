package pl.lodz.p.it.ssbd2021.ssbd01.mod.facades;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.MedicalDocumentation;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu MedicalDocumentation.
 */
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
