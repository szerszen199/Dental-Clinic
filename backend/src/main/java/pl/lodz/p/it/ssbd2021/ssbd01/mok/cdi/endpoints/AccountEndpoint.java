package pl.lodz.p.it.ssbd2021.ssbd01.mok.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.common.RolesStringsTmp;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordTooShortException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordsNotMatchException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.AccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.AccountAccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.AccountEditDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.NewAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.NewPasswordDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccessLevelManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtEmailConfirmationUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.converters.AccountConverter;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Typ Account endpoint.
 */
@Path("account")
@RequestScoped
public class AccountEndpoint {

    @EJB
    private JwtEmailConfirmationUtils jwtEmailConfirmationUtils;

    @Inject
    private AccountManager accountManager;

    @Inject
    private AccessLevelManager accessLevelManager;

    /**
     * Tworzy nowe konto.
     *
     * @param newAccountDto obiekt zawierający login, email, hasło i inne wymagane dane
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @POST
    @PermitAll
    @Path("create")
    @Consumes({MediaType.APPLICATION_JSON})
    public void createAccount(NewAccountDto newAccountDto) throws AppBaseException {
        accountManager.createAccount(AccountConverter.createAccountEntityFromDto(newAccountDto));
    }

    /**
     * Confirm account.
     *
     * @param jwt jwt
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/confirm/{jwt}
    @PUT
    @Path("confirm/{jwt}")
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    public void confirmAccount(@PathParam("jwt") String jwt) {
        if (jwtEmailConfirmationUtils.validateRegistrationConfirmationJwtToken(jwt)) {
            try {
                accountManager.confirmAccount(jwtEmailConfirmationUtils.getUserNameFromRegistrationConfirmationJwtToken(jwt));
            } catch (ParseException | AppBaseException e) {
                // TODO: 18.04.2021
                e.printStackTrace();
            }
        }
    }

    /**
     * Edit account data.
     *
     * @param accountDto Account with edited data.
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/edit
    @POST
    @Path("edit")
    @RolesAllowed({RolesStringsTmp.receptionist, RolesStringsTmp.doctor, RolesStringsTmp.admin, RolesStringsTmp.user})
    @Produces({MediaType.APPLICATION_JSON})
    public void editAccount(AccountEditDto accountDto) throws AppBaseException {
        Account account = accountManager.getLoggedInAccount();
        accountManager.editAccount(AccountConverter.createAccountEntityFromDto(accountDto, account));
    }


    /**
     * Edycja konta innego użytkownika.
     *
     * @param accountDto the edited account
     * @param login  login edytowanego konta
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/edit/other
    @POST
    @Path("edit/{login}")
    @RolesAllowed({RolesStringsTmp.admin})
    @Produces({MediaType.APPLICATION_JSON})
    public void editOtherAccount(@PathParam("login") String login, AccountEditDto accountDto) throws AppBaseException {
        Account account = accountManager.findByLogin(login);
        accountManager.editOtherAccount(AccountConverter.createAccountEntityFromDto(accountDto, account));
    }

    /**
     * Revoke access level - enpoint odbierający poziom dostępu {@param level} dla użytkownika o {@param id}.
     *
     * @param id    id uzytkownika, któremu zostanie odebrany poziom dostępu
     * @param level level odbierany poziom odstępu
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/revokeAccessLevel/{id}/{level}
    @PUT
    @RolesAllowed({RolesStringsTmp.admin})
    @Path("/revokeAccessLevelById/{id}/{level}")
    @Produces({MediaType.APPLICATION_JSON})
    public void revokeAccessLevel(@PathParam("id") Long id, @PathParam("level") String level) throws AppBaseException {
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
    @RolesAllowed({RolesStringsTmp.admin})
    @Path("/revokeAccessLevelByLogin/{login}/{level}")
    @Produces({MediaType.APPLICATION_JSON})
    public void revokeAccessLevel(@PathParam("login") String login, @PathParam("level") String level) throws AppBaseException {
        accessLevelManager.revokeAccessLevel(login, level);
    }

    /**
     * Metoda służąca do blokowania konta przez administratora.
     *
     * @param id id blokowanego konta
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @RolesAllowed({RolesStringsTmp.admin})
    @Path("lock/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public void lockAccount(@PathParam("id") Long id) throws AppBaseException {
        accountManager.lockAccount(id);
    }

    /**
     * Metoda służąca do odblokowywania konta przez administratora.
     *
     * @param id id odblokowywanego konta
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @RolesAllowed({RolesStringsTmp.admin})
    @Path("unlock/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public void unlockAccount(@PathParam("id") Long id) throws AppBaseException {
        accountManager.unlockAccount(id);
    }

    /**
     * Dodanie poziomu dostępu {@param level} dla użytkownika o {@param login}.
     *
     * @param accessLevelDto obiekt zawierający poziom oraz login
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @RolesAllowed({RolesStringsTmp.admin})
    @Path("/addLevelByLogin")
    @Produces({MediaType.APPLICATION_JSON})
    public void addAccessLevel(AccessLevelDto accessLevelDto) throws AppBaseException {
        accessLevelManager.addAccessLevel(accessLevelDto.getLogin(), accessLevelDto.getLevel());
    }

    /**
     * Pobiera informacje o zalogowanm koncie.
     *
     * @return informacje o zalogowanym koncie
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @GET
    @RolesAllowed({RolesStringsTmp.receptionist, RolesStringsTmp.doctor, RolesStringsTmp.admin, RolesStringsTmp.user})
    @Path("/info")
    public Response getLoggedInAccountInfo() throws AppBaseException {
        AccountDto account = new AccountDto(accountManager.getLoggedInAccount());
        return Response.ok(account).build();
    }


    /**
     * Pobiera informacje o koncie o {@param login}.
     *
     * @param login login konta o jakim pobrane zostaną informacje
     * @return informacje o zalogowanym koncie
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @GET
    @RolesAllowed({RolesStringsTmp.admin})
    @Path("/info/{login}")
    public Response getAccountInfoWithLogin(@PathParam("login") String login) throws AppBaseException {
        AccountDto account = new AccountDto(accountManager.findByLogin(login));
        return Response.ok(account).build();
    }

    /**
     * Pobiera listę wszystkich kont.
     *
     * @return lista wszystkich kont
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @GET
    @RolesAllowed({RolesStringsTmp.admin})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/accounts")
    public Response getAllAccounts() throws AppBaseException {
        List<AccountDto> accountDtoList = accountManager.getAllAccounts()
                .stream()
                .map(AccountDto::new)
                .collect(Collectors.toList());
        return Response.ok(accountDtoList).build();
    }

    /**
     * Pobiera listę wszystkich kont z poziomami dostępu.
     *
     * @return lista wszystkich kont
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @GET
    @RolesAllowed({RolesStringsTmp.admin})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/accounts-with-levels")
    public Response getAllAccountsWithLevels() throws AppBaseException {
        List<AccountAccessLevelDto> accountDtoList = accountManager.getAllAccounts()
                .stream()
                .map(AccountAccessLevelDto::new)
                .collect(Collectors.toList());
        return Response.ok(accountDtoList).build();
    }

    /**
     * Zmienia hasło do własnego konta.
     *
     * @param newPassword informacje uwierzytelniające o starym haśle i nowym, które ma zostać ustawione
     * @return odpowiedź na żądanie
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @RolesAllowed({RolesStringsTmp.receptionist, RolesStringsTmp.doctor, RolesStringsTmp.admin, RolesStringsTmp.user})
    @Path("new-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeOwnPassword(NewPasswordDto newPassword) throws AppBaseException {
        Account account = accountManager.getLoggedInAccount();
        if (account == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }

        try {
            this.validatePassword(newPassword);
            accountManager.changePassword(account, newPassword.getOldPassword(), newPassword.getFirstPassword());
            return Response.status(Status.OK).build();
        } catch (AppBaseException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Resetuje hasło innego użytkownika z poziomu konta administratora.
     *
     * @param id id konta, którego hasło chcemy zresetować
     * @return odpowiedź na żądanie
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @RolesAllowed({RolesStringsTmp.admin})
    @Path("reset-password/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resetOthersPassword(@PathParam("id") Long id) throws AppBaseException {
        Account account = accountManager.getLoggedInAccount();
        if (account == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        if (!accountManager.isAdmin(account)) {
            return Response.status(Status.UNAUTHORIZED).build();
        }

        accountManager.resetPassword(id);
        return Response.status(Status.OK).build();
    }

    /**
     * Resetuje hasło zalogowanemu użytkownikowi.
     *
     * @return odpowiedź na żądanie
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @Path("reset-password")
    @RolesAllowed({RolesStringsTmp.receptionist, RolesStringsTmp.doctor, RolesStringsTmp.admin, RolesStringsTmp.user})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resetOwnPassword() throws AppBaseException {
        Account account = accountManager.getLoggedInAccount();
        if (account == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        if (!account.getActive()) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        accountManager.resetPassword(account);
        return Response.status(Status.OK).build();
    }


    /**
     * Endpoint dla ustawiania w aktualnym koncie trybu ciemnego.
     *
     * @param isDarkMode true jeśli chcemy ustawić tryb ciemny, inaczej false.
     * @return Response 401 jeśli użytkownik jest unauthorised, 200 jeśli udało się ustawić tryb ciemny, inaczej 400
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @Path("dark-mode")
    @RolesAllowed({RolesStringsTmp.receptionist, RolesStringsTmp.doctor, RolesStringsTmp.admin, RolesStringsTmp.user})
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeDarkMode(@QueryParam("dark-mode") boolean isDarkMode) throws AppBaseException {
        Account account = accountManager.getLoggedInAccount();
        if (account == null) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        try {
            accountManager.setDarkMode(account, isDarkMode);
        } catch (AppBaseException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Status.OK).build();
    }

    private void validatePassword(NewPasswordDto newPassword) throws AppBaseException {
        if (!newPassword.getFirstPassword().equals(newPassword.getSecondPassword())) {
            throw PasswordsNotMatchException.newPasswordsNotMatch();
        }
        if (newPassword.getFirstPassword().length() < 8) {
            throw PasswordTooShortException.passwordTooShort();
        }
    }
}

