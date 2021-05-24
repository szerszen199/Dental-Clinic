package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.IpAddressUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;

import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;

/**
 * Typ Access level manager implementation - implementacja AccessLevelManager.
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class AccessLevelManagerImplementation extends AbstractManager implements AccessLevelManager, SessionSynchronization {
    @Inject
    private AccessLevelFacade accessLevelFacade;

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private LoggedInAccountUtil loggedInAccountUtil;
    @Inject
    private HttpServletRequest httpServletRequest;

    @Override
    public void revokeAccessLevel(String login, String level) throws AppBaseException {
        AccessLevel accessLevel = accessLevelFacade.findByAccountLoginAndAccessLevel(login, level);
        if (accessLevel.getActive()) {
            accessLevel.setActive(false);
            accessLevel.setModifiedBy(accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
            accessLevel.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(httpServletRequest));
            accessLevelFacade.edit(accessLevel);
        }
    }

    @Override
    public void addAccessLevel(String login, String level) throws AppBaseException {
        AccessLevel accessLevel = accessLevelFacade.findByAccountLoginAndAccessLevel(login, level);
        if (!accessLevel.getActive()) {
            accessLevel.setActive(true);
            accessLevel.setModifiedBy(accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
            accessLevel.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(httpServletRequest));
            accessLevelFacade.edit(accessLevel);
        }
    }

}
