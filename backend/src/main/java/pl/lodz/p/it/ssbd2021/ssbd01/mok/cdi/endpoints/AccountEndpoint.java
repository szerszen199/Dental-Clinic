package pl.lodz.p.it.ssbd2021.ssbd01.mok.cdi.endpoints;

import java.text.ParseException;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.AccountDto;

import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccessLevelManager;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.BaseException;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AccessLevelException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.converters.AccountConverter;



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


    /**
     * Tworzy nowe konto.
     *
     * @param accountDto obiekt zawierający login, email, hasło i inne wymagane dane
     */
    @POST
    @Path("create")
    @Consumes({MediaType.APPLICATION_JSON})
    public void createAccount(AccountDto accountDto) {
        accountManager.createAccount(AccountConverter.createAccountEntityFromDto(accountDto), new PatientData());
    }


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

    @POST
    @Path("edit/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public void editAccount(@PathParam("id") Long id, AccountDto accountDto) throws BaseException {
        accountManager.editAccount(id,AccountConverter.createAccountEntityFromDto(accountDto));
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

    /**
     * Dodanie poziomu dostępu {@param level} dla użytkownika o id równym {@param id}.
     *
     * @param id    id uzytkownika, któremu zostanie dodany poziom dostępu
     * @param level dodawany poziom odstępu
     * @throws AccessLevelException wyjątek gdy nie ma takiego poziomu dostępu
     */
    @PUT
    @Path("/addLevelById/{id}/{level}")
    @Produces({MediaType.APPLICATION_JSON})
    public void addAccessLevel(@PathParam("id") Long id, @PathParam("level") String level) throws AccessLevelException {
        accountManager.addAccessLevel(id, level);
    }

    /**
     * Dodanie poziomu dostępu {@param level} dla użytkownika o {@param login}.
     *
     * @param login login uzytkownika, któremu zostanie dodany poziom dostępu
     * @param level dodawany poziom odstępu
     * @throws AccessLevelException wyjątek gdy nie ma takiego poziomu dostępu
     */
    @PUT
    @Path("/addLevelByLogin/{login}/{level}")
    @Produces({MediaType.APPLICATION_JSON})
    public void addAccessLevel(@PathParam("login") String login, @PathParam("level") String level) throws AccessLevelException {
        accountManager.addAccessLevel(login, level);
    }

}
