package pl.lodz.p.it.ssbd2021.ssbd01.mok.facades;

import javax.persistence.EntityManager;
import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;

public class AccessLevelFacade extends AbstractFacade<AccessLevel> {

    public AccessLevelFacade(Class<AccessLevel> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}
