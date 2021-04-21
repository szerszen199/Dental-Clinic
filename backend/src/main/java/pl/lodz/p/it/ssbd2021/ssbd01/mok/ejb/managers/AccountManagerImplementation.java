package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import java.util.Set;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AccessLevelMapper;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AccessLevelException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccessLevelFacade;
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
    public void addAccessLevel(Long id, String level) throws AccessLevelException {
        Account account = accountFacade.find(id);
        AccessLevel accessLevel = AccessLevelMapper.mapLevelNameToAccessLevel(level);
        accessLevel.setAccountId(account);
        accessLevel.setCreatedBy(account); //TODO FIX THIS -- only for debugging
        account.getAccessLevels().add(accessLevel);
    }

    @Override
    public void addAccessLevel(String login, String level) throws AccessLevelException {
        Account account = accountFacade.findByLogin(login);
        AccessLevel accessLevel = AccessLevelMapper.mapLevelNameToAccessLevel(level);
        accessLevel.setAccountId(account);
        accessLevel.setCreatedBy(account); //TODO FIX THIS -- only for debugging
        account.getAccessLevels().add(accessLevel);
    }



}
