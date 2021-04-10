package pl.lodz.p.it.ssbd2021.ssbd01.mok.facades;

import javax.persistence.EntityManager;
import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu Account.
 */
public class AccountFacade extends AbstractFacade<Account> {

    /**
     * Tworzy nową instancję klasy AccountFacade.
     *
     * @param entityClass entity class
     */
    public AccountFacade(Class<Account> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}
