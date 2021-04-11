package pl.lodz.p.it.ssbd2021.ssbd01.mow.facades;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu AccessLevel.
 */
public class AccessLevelFacade extends AbstractFacade<AccessLevel> {

    @PersistenceContext(unitName = "ssbd01mowPU")
    private EntityManager em;

    /**
     * Tworzy nową instancję klasy AccessLevelFacade.
     */
    public AccessLevelFacade() {
        super(AccessLevel.class);
    }

    /**
     * Tworzy nową instancję klasy AccessLevelFacade.
     *
     * @param entityClass typ obiektowy encji.
     */
    public AccessLevelFacade(Class<AccessLevel> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
