package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DocumentationEntry;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu DocumentationEntry.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LogInterceptor.class)
public class DocumentationEntryFacade extends AbstractFacade<DocumentationEntry> {

    @PersistenceContext(unitName = "ssbd01modPU")
    private EntityManager em;

    /**
     * Tworzy nową instancję klasy DocumentationEntryFacade.
     */
    public DocumentationEntryFacade() {
        super(DocumentationEntry.class);
    }

    /**
     * Tworzy nową instancję klasy DocumentationEntryFacade.
     *
     * @param entityClass typ obiektowy encji
     */
    public DocumentationEntryFacade(Class<DocumentationEntry> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
