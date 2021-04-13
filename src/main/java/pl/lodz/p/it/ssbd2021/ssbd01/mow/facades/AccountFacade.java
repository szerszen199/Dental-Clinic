package pl.lodz.p.it.ssbd2021.ssbd01.mow.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu Account.
 */
@Stateless
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "ssbd01mowPU")
    private EntityManager em;

    /**
     * Tworzy nową instancję klasy AccountFacade.
     */
    public AccountFacade() {
        super(Account.class);
    }

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
        return em;
    }
}
