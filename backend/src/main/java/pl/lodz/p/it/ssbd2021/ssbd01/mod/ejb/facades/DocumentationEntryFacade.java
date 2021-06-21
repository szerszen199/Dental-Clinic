package pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.facades;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DocumentationEntry;
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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu DocumentationEntry.
 */
@Stateless
@PermitAll
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
    public void remove(DocumentationEntry entity) {
        Query query = em.createNamedQuery("DocumentationEntry.deleteById");
        query.setParameter("id", entity.getId());
        query.executeUpdate();
    }

    public List<DocumentationEntry> getDocumentationEntriesByLogin(String login) throws AppBaseException {
        try {
            TypedQuery<DocumentationEntry> tq = em.createNamedQuery("DocumentationEntry.findByPatientLogin", DocumentationEntry.class);
            tq.setParameter("login", login);
            return tq.getResultList();
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
