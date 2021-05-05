package pl.lodz.p.it.ssbd2021.ssbd01.mok.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AccessLevelException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.BaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordTooShortException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordsNotMatchException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.AccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.NewPasswordDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccessLevelManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.converters.AccessLevelConverter;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.converters.AccountConverter;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;


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

    /**
     * Edit account data.
     *
     * @param accountDto Account with edited data.
     * @throws BaseException Base exception.
     */
    @POST
    @Path("edit")
    @Produces({MediaType.APPLICATION_JSON})
    public void editAccount(AccountDto accountDto) throws BaseException {
        accountManager.editAccount(AccountConverter.createAccountEntityFromDto(accountDto));
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
     * Dodanie poziomu dostępu {@param level} dla użytkownika o {@param login}.
     *
     * @param accessLevelDto obiekt zawierający poziom oraz login
     * @throws AccessLevelException wyjątek gdy nie ma takiego poziomu dostępu
     */
    @PUT
    @Path("/addLevelByLogin")
    @Produces({MediaType.APPLICATION_JSON})
    public void addAccessLevel(AccessLevelDto accessLevelDto) throws AccessLevelException {
        accountManager.addAccessLevel(AccessLevelConverter.createAccessLevelEntityFromDto(accessLevelDto), accessLevelDto.getLogin());
    }

    /**
     * Pobiera informacje o zalogowanm koncie.
     *
     * @return informacje o zalogowanym koncie
     */
    @GET
    @Path("/info")
    public Response getLoggedInAccountInfo() {
        AccountDto account = new AccountDto(accountManager.getLoggedInAccount());
        return Response.ok(account).build();
    }


    /**
     * Pobiera informacje o koncie o {@param login}.
     *
     * @param login login konta o jakim pobrane zostaną informacje
     * @return informacje o zalogowanym koncie
     */
    @GET
    @Path("/info/{login}")
    public Response getAccountInfoWithLogin(@PathParam("login") String login) {
        AccountDto account = new AccountDto(accountManager.findByLogin(login));
        return Response.ok(account).build();
    }

    /**
     * Pobiera listę wszystkich kont.
     *
     * @return lista wszystkich kont
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllAccounts() {
        List<AccountDto> accountDtoList = accountManager.getAllAccounts()
                .stream()
                .map(AccountDto::new)
                .collect(Collectors.toList());
        return Response.ok(accountDtoList).build();
    }

    /**
     * Zmienia hasło do własnego konta.
     *
     * @param newPassword informacje uwierzytelniające o starym haśle i nowym,
     *                    które ma zostać ustawione
     * @return odpowiedź na żądanie
     */
    @PUT
    @Path("new-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeOwnPassword(NewPasswordDTO newPassword) {
        Account account = accountManager.getLoggedInAccount();
        if (account == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }

        try {
            this.validatePassword(newPassword);
            accountManager.changePassword(account, newPassword.getOldPassword(), newPassword.getFirstPassword());
            return Response.status(Status.OK).build();
        } catch (BaseException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    private void validatePassword(NewPasswordDTO newPassword) throws BaseException {
        if (!newPassword.getFirstPassword().equals(newPassword.getSecondPassword())) {
            throw new PasswordsNotMatchException(PasswordsNotMatchException.NEW_PASSWORDS_NOT_MATCH);
        }
        if (newPassword.getFirstPassword().length() < 8) {
            throw new PasswordTooShortException(PasswordTooShortException.PASSWORD_TOO_SHORT);
        }
    }


}

