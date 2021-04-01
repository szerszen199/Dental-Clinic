package pl.lodz.p.it.ssbd2021.ssbd01.mok.facades;

import javax.persistence.EntityManager;
import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;

public class AccountFacade extends AbstractFacade<Account> {

    public AccountFacade(Class<Account> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}
