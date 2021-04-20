package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.BaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccountFacade;


/**
 * Typ Account manager implementation.
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountManagerImplementation implements AccountManager {
    @Inject
    private AccountFacade accountFacade;

    @Override
    public void confirmAccount(Long id) {
        accountFacade.find(id).setEnabled(true);
    }

    @Override
    public void confirmAccount(String login) {
        accountFacade.findByLogin(login).setEnabled(true);
    }

    @Override
    public void lockAccount(Long id) throws BaseException {
        Account account = accountFacade.find(id);
        account.setActive(false);
        accountFacade.edit(account);
    }

    @Override
    public void unlockAccount(Long id) throws BaseException {
        Account account = accountFacade.find(id);
        account.setActive(true);
        accountFacade.edit(account);
    }
}
