package pl.lodz.p.it.ssbd2021.ssbd01.auth.ejb.managers;


import pl.lodz.p.it.ssbd2021.ssbd01.auth.ejb.facades.AuthViewEntityFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AuthViewEntity;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccessLevelFacade;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.core.Context;
import java.util.List;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AuthViewEntityManagerImpl implements AuthViewEntityManager {

    @Inject
    private AuthViewEntityFacade authViewEntityFacade;


    @Override
    public List<AuthViewEntity> findByLogin(String login) throws AppBaseException {
        return authViewEntityFacade.findByLogin(login);
    }

}
