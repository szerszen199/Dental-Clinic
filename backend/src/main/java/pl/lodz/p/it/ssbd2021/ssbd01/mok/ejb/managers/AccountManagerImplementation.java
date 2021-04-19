package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccessLevelFacade;

import java.util.Set;


/**
 * Typ Account manager implementation.
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountManagerImplementation implements AccountManager {
    @Inject
    private AccountFacade accountFacade;

    @Inject
    private AccessLevelFacade accessLevelFacade;

    @Override
    public void confirmAccount(Long id) {
        accountFacade.find(id).setEnabled(true);
    }

    @Override
    public void confirmAccount(String login) {
        accountFacade.findByLogin(login).setEnabled(true);
    }

    @Override
    public void revokeAccessLevel(Long id, String level) {
        Set<AccessLevel> accessLevelSet = accountFacade.find(id).getAccessLevels();
        for (var a : accessLevelSet
        ) {
            if(a.getLevel().equals(level) && a.getActive()){
                a.setActive(false);
            }
        }
    }

    @Override
    public void revokeAccessLevel(String login, String level) {
        Set<AccessLevel> accessLevelSet = accountFacade.findByLogin(login).getAccessLevels();
        for (var a : accessLevelSet
             ) {
            if(a.getLevel().equals(level) && a.getActive()){
                a.setActive(false);
            }
        }
    }
}
