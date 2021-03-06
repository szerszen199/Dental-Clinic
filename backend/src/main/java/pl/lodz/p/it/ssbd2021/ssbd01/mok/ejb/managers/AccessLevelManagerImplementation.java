package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorRating;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccessLevelException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.DoctorRatingFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.IpAddressUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;

import javax.annotation.security.PermitAll;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Typ Access level manager implementation - implementacja AccessLevelManager.
 */
@Stateful
@PermitAll
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class AccessLevelManagerImplementation extends AbstractManager implements AccessLevelManager, SessionSynchronization {
    @Inject
    private AccessLevelFacade accessLevelFacade;

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private DoctorRatingFacade doctorRatingFacade;

    @Inject
    private LoggedInAccountUtil loggedInAccountUtil;
    @Inject
    private HttpServletRequest httpServletRequest;

    @Override
    public void revokeAccessLevel(String login, String level) throws AppBaseException {
        AccessLevel accessLevel;
        try {
            accessLevel = accessLevelFacade.findByAccountLoginAndAccessLevel(login, level);
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (Exception e) {
            throw AccessLevelException.accessLevelRevokeFailed();
        }
        if (accessLevel.getActive()) {
            accessLevel.setActive(false);
            accessLevel.setModifiedBy(accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
            accessLevel.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(httpServletRequest));
            try {
                accessLevelFacade.edit(accessLevel);
            } catch (Exception e) {
                throw AccessLevelException.accessLevelRevokeFailed();
            }
        }
    }

    @Override
    public void addAccessLevel(String login, String level) throws AppBaseException {
        AccessLevel accessLevel;
        try {
            accessLevel = accessLevelFacade.findByAccountLoginAndAccessLevel(login, level);
        } catch (AccountException e) {
            throw AccountException.noSuchAccount(e.getCause());
        } catch (Exception e) {
            throw AccessLevelException.accessLevelAddFailed();
        }

        if (!accessLevel.getActive()) {
            accessLevel.setActive(true);
            accessLevel.setModifiedBy(accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
            accessLevel.setModifiedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(httpServletRequest));

            if (accessLevel.getLevel().equals(I18n.DOCTOR)) {

                Optional<DoctorRating> doctorRatingOptional;

                try {
                    doctorRatingOptional = doctorRatingFacade.findByDoctorLogin(login);
                } catch (Exception e) {
                    throw AccessLevelException.accessLevelAddFailed();
                }

                if (doctorRatingOptional.isEmpty()) {
                    DoctorRating doctorRating = new DoctorRating(accessLevel.getAccountId());
                    doctorRating.setCreatedBy(accountFacade.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
                    doctorRating.setCreatedByIp(IpAddressUtils.getClientIpAddressFromHttpServletRequest(httpServletRequest));
                    try {
                        doctorRatingFacade.create(doctorRating);
                    } catch (Exception e) {
                        throw AccessLevelException.accessLevelAddFailed();
                    }
                }
            }

            try {
                accessLevelFacade.edit(accessLevel);
            } catch (Exception e) {
                throw AccessLevelException.accessLevelAddFailed();
            }
        }

    }

    @Override
    public void deleteAccessLevelsByAccountId(Long id) throws AppBaseException {
        var x = accessLevelFacade.findByAccountId(id);
        for (AccessLevel al: x) {
            accessLevelFacade.remove(al);
        }
    }

}
