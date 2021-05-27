package pl.lodz.p.it.ssbd2021.ssbd01.auth.ejb.managers;


import pl.lodz.p.it.ssbd2021.ssbd01.auth.ejb.facades.AuthViewEntityFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AuthViewEntity;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.AbstractManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LogInterceptor.class)
public class AuthViewEntityManagerImpl extends AbstractManager implements AuthViewEntityManager, SessionSynchronization {

    @Inject
    private AuthViewEntityFacade authViewEntityFacade;


    @Override
    public List<AuthViewEntity> findByLogin(String login) throws AppBaseException {
        return authViewEntityFacade.findByLogin(login);
    }

}
