package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import java.util.Set;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
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
    public void editAccountAccessLevels(Account account, Set<AccessLevel> accessLevels) {
        account.getAccessLevels().retainAll(accessLevels);
        account.getAccessLevels().addAll(accessLevels);
        accountFacade.edit(account);
    }


}
