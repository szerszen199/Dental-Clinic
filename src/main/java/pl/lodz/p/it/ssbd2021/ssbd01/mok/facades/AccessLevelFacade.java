package pl.lodz.p.it.ssbd2021.ssbd01.mok.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu AccessLevel.
 */
@Stateless
public class AccessLevelFacade extends AbstractFacade<AccessLevel> {

    @PersistenceContext(unitName = "ssbd01mokPU")
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
