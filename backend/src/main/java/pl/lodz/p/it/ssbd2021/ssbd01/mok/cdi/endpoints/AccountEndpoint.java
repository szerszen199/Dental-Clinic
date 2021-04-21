package pl.lodz.p.it.ssbd2021.ssbd01.mok.cdi.endpoints;

import java.text.ParseException;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccessLevelManager;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.BaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtUtils;


/**
 * Typ Account endpoint.
 */
@Path("account")
@RequestScoped
public class AccountEndpoint {

    @EJB
    private JwtUtils jwtUtils;

    @Inject
    private AccountManager accountManager;

    @Inject
    private AccessLevelManager accessLevelManager;

    /**
     * Confirm account.
     *
     * @param jwt jwt
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/confirm/{jwt}
    @PUT
    @Path("confirm/{jwt}")
    @Produces({MediaType.APPLICATION_JSON})
    public void confirmAccount(@PathParam("jwt") String jwt) {
        if (jwtUtils.validateRegistrationConfirmationJwtToken(jwt)) {
            try {
                accountManager.confirmAccount(jwtUtils.getUserNameFromRegistrationConfirmationJwtToken(jwt));
            } catch (ParseException e) {
                // TODO: 18.04.2021
                e.printStackTrace();
            }
        }
    }


    /**
     * Revoke access level - enpoint odbierający poziom dostępu {@param level} dla użytkownika o {@param id}.
     *
     * @param id    id uzytkownika, któremu zostanie odebrany poziom dostępu
     * @param level level odbierany poziom odstępu
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/revokeAccessLevel/{id}/{level}
    @PUT
    @Path("/revokeAccessLevelById/{id}/{level}")
    @Produces({MediaType.APPLICATION_JSON})
    public void revokeAccessLevel(@PathParam("id") Long id, @PathParam("level") String level) {
        accessLevelManager.revokeAccessLevel(id, level);
    }

    /**
     * Revoke access level - enpoint odbierający poziom dostępu {@param level} dla użytkownika o {@param login}.
     *
     * @param login login uzytkownika, któremu zostanie odebrany poziom dostępu
     * @param level level odbierany poziom odstępu
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/revokeAccessLevel/{login}/{level}
    @PUT
    @Path("/revokeAccessLevelByLogin/{login}/{level}")
    @Produces({MediaType.APPLICATION_JSON})
    public void revokeAccessLevel(@PathParam("login") String login, @PathParam("level") String level) {
        accessLevelManager.revokeAccessLevel(login, level);
    }

    /**
     * Metoda służąca do blokowania konta przez administratora.
     *
     * @param id id blokowanego konta
     */
    @PUT
    @Path("lock/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public void lockAccount(@PathParam("id") Long id) {
        try {
            accountManager.lockAccount(id);
        } catch (BaseException e) {
            e.printStackTrace();
            // TODO: 20.04.2021 add application exception
        }
    }

    /**
     * Metoda służąca do odblokowywania konta przez administratora.
     *
     * @param id id odblokowywanego konta
     */
    @PUT
    @Path("unlock/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public void unlockAccount(@PathParam("id") Long id) {
        try {
            accountManager.unlockAccount(id);
        } catch (BaseException e) {
            e.printStackTrace();
            // TODO: 20.04.2021 add application exception
        }
    }
}
