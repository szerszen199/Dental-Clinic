package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.BaseException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu Account.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "ssbd01mokPU")
    private EntityManager em;

    /**
     * Tworzy nową instancję klasy AccountFacade.
     */
    public AccountFacade() {
        super(Account.class);
    }

    /**
     * Find by login account.
     *
     * @param login login
     * @return account
     */
    public Account findByLogin(String login) {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findByLogin", Account.class);
        tq.setParameter("login", login);
        return tq.getSingleResult();
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

    @Override
    public void edit(Account entity) throws BaseException {
        try {
            Account oldAcc = findByLogin(entity.getLogin());
            Account newAcc = new Account(oldAcc.getId(), entity.getLogin(), entity.getEmail(),
                    entity.getPassword(), entity.getFirstName(), entity.getLastName(), entity.getPhoneNumber(), entity.getPesel());
            super.edit(newAcc);
        } catch (BaseException e) {
            throw e;
            // TODO: 20.04.2021 - uzupełnić o wyjątki aplikacyjne
        }
    }


}
