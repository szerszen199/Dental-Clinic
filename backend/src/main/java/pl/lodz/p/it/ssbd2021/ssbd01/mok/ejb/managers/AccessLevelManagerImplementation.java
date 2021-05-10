package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccessLevelFacade;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Typ Access level manager implementation - implementacja AccessLevelManager.
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccessLevelManagerImplementation implements AccessLevelManager {
    @Inject
    private AccessLevelFacade accessLevelFacade;


    @Override
    public void revokeAccessLevel(Long id, String level) {
        AccessLevel accessLevel = accessLevelFacade.findByAccountIdAndAccessLevel(id, level);
        if (accessLevel.getActive()) {
            accessLevel.setActive(false);
        }
    }

    @Override
    public void revokeAccessLevel(String login, String level) {
        AccessLevel accessLevel = accessLevelFacade.findByAccountLoginAndAccessLevel(login, level);
        if (accessLevel.getActive()) {
            accessLevel.setActive(false);
        }
    }

    @Override
    public void addAccessLevel(String login, String level) {
        AccessLevel accessLevel = accessLevelFacade.findByAccountLoginAndAccessLevel(login, level);
        if (!accessLevel.getActive()) {
            accessLevel.setActive(true);
        }
    }

}
