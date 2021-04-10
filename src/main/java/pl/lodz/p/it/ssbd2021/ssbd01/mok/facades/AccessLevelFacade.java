package pl.lodz.p.it.ssbd2021.ssbd01.mok.facades;

import javax.persistence.EntityManager;
import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu AccessLevel.
 */
public class AccessLevelFacade extends AbstractFacade<AccessLevel> {

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
        return null;
    }
}
