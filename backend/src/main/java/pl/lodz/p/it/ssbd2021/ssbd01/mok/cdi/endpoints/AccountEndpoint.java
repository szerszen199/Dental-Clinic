package pl.lodz.p.it.ssbd2021.ssbd01.mok.cdi.endpoints;


import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.RevokeAndGrantAccessLevelDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.AccountInfoResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.ConfirmMailChangeRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.EditAnotherAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.EditOwnAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.SetLanguageRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.SetDarkModeRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.ConfirmAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.CreateAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.ChangePasswordRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.SimpleUsernameRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccessLevelManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.MailProvider;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.converters.AccountConverter;

/**
 * Typ Account endpoint.
 */
@Path("account")
@RequestScoped
@Interceptors(LogInterceptor.class)
public class AccountEndpoint {

    @Inject
    private AccountManager accountManager;

    @Inject
    private LoggedInAccountUtil loggedInAccountUtil;

    @Inject
    private AccessLevelManager accessLevelManager;

    @Inject
    private MailProvider mailProvider;

    /**
     * Tworzy nowe konto.
     *
     * @param accountDto     obiekt zawierający login, email, hasło i inne wymagane dane
     * @param servletContext kontekst serwletów, służy do współdzielenia informacji w ramach aplikacji
     * @return response
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @POST
    @PermitAll
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    // TODO: 22.05.2021 Co do validów na metodach zastanawia mnie wywołanie pustej metody na której będzie valid i łapanie wyjątków i coś robienie
    //  Ale generalnie mamy obsłużyć tylko 403, 404 i 500 a to zwraca 400 więc :p
    public Response createAccount(@NotNull @Valid CreateAccountRequestDTO accountDto, @Context ServletContext servletContext)
            throws AppBaseException {
        // TODO: 21.05.2021 Obsługa wyjątków
        // TODO: 22.05.2021 Walidacja numeru pesel jesli nie jest null (suma kontrolna)
        this.accountManager.createAccount(
                AccountConverter.createAccountEntityFromDto(accountDto),
                servletContext
        );

        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_CREATED_SUCCESSFULLY)).build();
    }

    /**
     * Confirm account.
     *
     * @param confirmAccountRequestDTO confirm account request dto
     * @return response
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/confirm?token={token}
    @PUT
    @Path("confirm")
    @PermitAll
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response confirmAccount(@NotNull @Valid ConfirmAccountRequestDTO confirmAccountRequestDTO) throws AppBaseException {
        // TODO: 21.05.2021 Obsługa wyjątków
        this.accountManager.confirmAccountByToken(confirmAccountRequestDTO.getConfirmToken());
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_CONFIRMED_SUCCESSFULLY)).build();
    }

    /**
     * Edit account data.
     *
     * @param accountDto     DTO edytowanego konta
     * @param servletContext kontekst serwletów, służy do współdzielenia informacji                       w ramach aplikacji
     * @return response
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/edit
    @POST
    @Path("edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.ADMIN, I18n.PATIENT})
    @Produces({MediaType.APPLICATION_JSON})
    public Response editAccount(@NotNull @Valid EditOwnAccountRequestDTO accountDto, @Context ServletContext servletContext) throws AppBaseException {
        // TODO: 21.05.2021 Obsługa wyjątków
        this.accountManager.editOwnAccount(accountDto, servletContext);
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_EDITED_SUCCESSFULLY)).build();
    }


    /**
     * Edycja konta innego użytkownika.
     *
     * @param accountDto     DTO edytowanego konta
     * @param servletContext kontekst serwletów, służy do współdzielenia informacji                       w ramach aplikacji
     * @return response
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/edit/other
    @POST
    @Path("edit-other")
    @RolesAllowed({I18n.ADMIN})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editOtherAccount(@NotNull @Valid EditAnotherAccountRequestDTO accountDto, @Context ServletContext servletContext) throws AppBaseException {
        // TODO: 21.05.2021 Obsługa wyjątków
        accountManager.editOtherAccount(accountDto, servletContext);
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_EDITED_SUCCESSFULLY)).build();
    }

    /**
     * Endpoint potwierdzający zmianę maila.
     *
     * @param confirmMailChangeRequestDTO confirm mail change request dto
     * @return response
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @Path("confirm-mail")
    // Rozumiem że to nie wymaga zalogowania
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response confirmMailChange(@NotNull @Valid ConfirmMailChangeRequestDTO confirmMailChangeRequestDTO) throws AppBaseException {
        // TODO: 21.05.2021 Obsługa wyjątków
        accountManager.confirmMailChangeByToken(confirmMailChangeRequestDTO.getToken());
        return Response.ok().entity(new MessageResponseDto(I18n.EMAIL_CONFIRMED_SUCCESSFULLY)).build();
    }

    /**
     * Revoke access level - enpoint odbierający poziom dostępu.
     *
     * @param revokeAndGrantAccessLevelDTO obiekt zawierający poziom oraz login
     * @return odpowiedź 400 gdy administrator próbuje sam sobie odebrać poziom dostępu, 200 gdy dodanie poprawne
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/revokeAccessLevel/{login}/{level}
    @PUT
    @RolesAllowed({I18n.ADMIN})
    @Path("/revokeAccessLevel")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response revokeAccessLevel(@NotNull @Valid RevokeAndGrantAccessLevelDTO revokeAndGrantAccessLevelDTO) throws AppBaseException {
        // TODO: 21.05.2021 Obsługa wyjątków
        if (revokeAndGrantAccessLevelDTO.getLogin().equals(loggedInAccountUtil.getLoggedInAccountLogin())) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        accessLevelManager.revokeAccessLevel(revokeAndGrantAccessLevelDTO.getLogin(), revokeAndGrantAccessLevelDTO.getLevel());
        return Response.ok().build();
    }

    /**
     * Metoda służąca do blokowania konta przez administratora.
     *
     * @param simpleUsernameRequestDTO simple username request dto
     * @return response
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @RolesAllowed({I18n.ADMIN})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("lock")
    @Produces({MediaType.APPLICATION_JSON})
    public Response lockAccount(@NotNull @Valid SimpleUsernameRequestDTO simpleUsernameRequestDTO) throws AppBaseException {
        // TODO: 21.05.2021 Obsługa wyjątków
        accountManager.lockAccount(simpleUsernameRequestDTO.getLogin());
        mailProvider.sendAccountLockByAdminMail(accountManager.findByLogin(simpleUsernameRequestDTO.getLogin()).getEmail());
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_LOCKED_SUCCESSFULLY)).build();
    }

    /**
     * Metoda służąca do odblokowywania konta przez administratora.
     *
     * @param simpleUsernameRequestDTO simple username request dto
     * @return response
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @RolesAllowed({I18n.ADMIN})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("unlock")
    @Produces({MediaType.APPLICATION_JSON})
    public Response unlockAccount(@NotNull @Valid SimpleUsernameRequestDTO simpleUsernameRequestDTO) throws AppBaseException {
        // TODO: 21.05.2021 Obsługa wyjątków
        accountManager.unlockAccount(simpleUsernameRequestDTO.getLogin());
        mailProvider.sendAccounUnlockByAdminMail(accountManager.findByLogin(simpleUsernameRequestDTO.getLogin()).getEmail());
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_UNLOCKED_SUCCESSFULLY)).build();
    }

    /**
     * Dodanie poziomu dostępu {@param level} dla użytkownika o {@param login}.
     *
     * @param revokeAndGrantAccessLevelDTO revoke and grant access level dto
     * @return @return odpowiedź 400 gdy administrator próbuje sam sobie dodać poziom dostępu, 200 gdy dodanie poprawne
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @RolesAllowed({I18n.ADMIN})
    @Path("/addLevelByLogin")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addAccessLevel(@NotNull @Valid RevokeAndGrantAccessLevelDTO revokeAndGrantAccessLevelDTO) throws AppBaseException {
        // TODO: 21.05.2021 Obsługa wyjątków
        if (revokeAndGrantAccessLevelDTO.getLogin().equals(loggedInAccountUtil.getLoggedInAccountLogin())) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        accessLevelManager.addAccessLevel(revokeAndGrantAccessLevelDTO.getLogin(), revokeAndGrantAccessLevelDTO.getLevel());
        return Response.ok().entity(new MessageResponseDto(I18n.ACCESS_LEVEL_ADDED_SUCCESSFULLY)).build();
    }

    /**
     * Pobiera informacje o zalogowanm koncie.
     *
     * @return informacje o zalogowanym koncie
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @GET
    @RolesAllowed({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.ADMIN, I18n.PATIENT})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/info")
    public Response getLoggedInAccountInfo() throws AppBaseException {
        // TODO: 21.05.2021 Obsługa wyjątków
        AccountInfoResponseDTO account = new AccountInfoResponseDTO(accountManager.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        return Response.ok().entity(account).build();
    }


    /**
     * Pobiera informacje o koncie o {@param login}.
     *
     * @param simpleUsernameRequestDTO simple username request dto
     * @return informacje o zalogowanym koncie
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.ADMIN})
    @Path("/other-account-info")
    public Response getAccountInfoWithLogin(@NotNull @Valid SimpleUsernameRequestDTO simpleUsernameRequestDTO) throws AppBaseException {
        // TODO: 21.05.2021 Obsługa wyjątków
        AccountInfoResponseDTO account = new AccountInfoResponseDTO(accountManager.findByLogin(simpleUsernameRequestDTO.getLogin()));
        return Response.ok().entity(account).build();
    }

    /**
     * Pobiera listę wszystkich kont.
     *
     * @return lista wszystkich kont
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @GET
    @RolesAllowed({I18n.ADMIN})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/accounts")
    public Response getAllAccounts() throws AppBaseException {
        // TODO: 21.05.2021 Obsługa Wyjątków
        List<AccountInfoResponseDTO> accountInfoResponseDTOList = accountManager.getAllAccounts()
                .stream()
                .map(AccountInfoResponseDTO::new)
                .collect(Collectors.toList());
        return Response.ok(accountInfoResponseDTOList).build();
    }

    // TODO: 21.05.2021 To jest niepotrzebne bo teraz zawsze info jest zwracane wraz z poziomami dostepu :o

    //    /**
    //     * Pobiera listę wszystkich kont z poziomami dostępu.
    //     *
    //     * @return lista wszystkich kont
    //     * @throws AppBaseException wyjątek typu AppBaseException
    //     */
    //    @GET
    //    @RolesAllowed({I18n.ADMIN})
    //    @Produces({MediaType.APPLICATION_JSON})
    //    @Path("/accounts-with-levels")
    //    public Response getAllAccountsWithLevels() throws AppBaseException {
    //        List<AccountAccessLevelDto> accountDtoList = accountManager.getAllAccounts()
    //                .stream()
    //                .map(AccountAccessLevelDto::new)
    //                .collect(Collectors.toList());
    //        return Response.ok(accountDtoList).build();
    //    }

    /**
     * Zmienia hasło do własnego konta.
     *
     * @param newPassword informacje uwierzytelniające o starym haśle i nowym, które ma zostać ustawione
     * @return odpowiedź na żądanie
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @RolesAllowed({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.ADMIN, I18n.PATIENT})
    @Path("new-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeOwnPassword(@NotNull @Valid ChangePasswordRequestDTO newPassword) {
        // TODO: 21.05.2021 Lepsza obsługa wyjątków ;)
        try {
            accountManager.changePassword(
                    loggedInAccountUtil.getLoggedInAccountLogin(),
                    newPassword.getOldPassword(),
                    newPassword.getFirstPassword()
            );
            return Response.status(Status.OK).entity(new MessageResponseDto(I18n.PASSWORD_CHANGED_SUCCESSFULLY)).build();
        } catch (AppBaseException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Resetuje hasło innego użytkownika z poziomu konta administratora.
     *
     * @param simpleUsernameRequestDTO simple username request dto
     * @return odpowiedź na żądanie
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @RolesAllowed({I18n.ADMIN})
    @Path("reset-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resetOthersPassword(@NotNull @Valid SimpleUsernameRequestDTO simpleUsernameRequestDTO) throws AppBaseException {
        // TODO: 21.05.2021 Obsługa wyjątków.
        accountManager.resetPassword(simpleUsernameRequestDTO.getLogin());
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.PASSWORD_RESET_SUCCESSFULLY)).build();
    }

    /**
     * Resetuje hasło zalogowanemu użytkownikowi.
     *
     * @return odpowiedź na żądanie
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @Path("reset-other-password")
    @RolesAllowed({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.ADMIN, I18n.PATIENT})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resetOwnPassword() throws AppBaseException {
        // TODO: 21.05.2021  Ob słu ga Wy jąt ków
        String login = loggedInAccountUtil.getLoggedInAccountLogin();
        accountManager.resetPassword(login);
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.PASSWORD_RESET_SUCCESSFULLY)).build();
    }


    /**
     * Endpoint dla ustawiania w aktualnym koncie trybu ciemnego.
     *
     * @param setDarkModeRequestDTO dto zawierające ustawienia trybu ciemnego.
     * @return 200 jeśli udało się ustawić tryb ciemny, inaczej 400
     */
    @PUT
    @Path("dark-mode")
    @RolesAllowed({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.ADMIN, I18n.PATIENT})
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeDarkMode(@NotNull @Valid SetDarkModeRequestDTO setDarkModeRequestDTO) {
        // TODO: 22.05.2021 OB SLU GA WY JAT KOW
        String login = loggedInAccountUtil.getLoggedInAccountLogin();
        try {
            accountManager.setDarkMode(login, setDarkModeRequestDTO.isDarkMode());
        } catch (AppBaseException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Status.OK).entity(I18n.DARK_MODE_SET_SUCCESSFULLY).build();
    }


    /**
     * Endpoint dla ustawiania w aktualnym koncie języka interfejsu.
     *
     * @param setLanguageRequestDTO dto z pożądanym przez użytkownka językiem
     * @return 200 jeśli udało się zmienić język, inaczej 400
     */
    @PUT
    @Path("language")
    @RolesAllowed({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.ADMIN, I18n.PATIENT})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeLanguage(@NotNull @Valid SetLanguageRequestDTO setLanguageRequestDTO) {
        // TODO: 22.05.2021 Ob Słu ga Wiadomo czego
        String login = loggedInAccountUtil.getLoggedInAccountLogin();
        try {
            accountManager.setLanguage(login, setLanguageRequestDTO.getLanguage().toLowerCase(Locale.ROOT));
        } catch (AppBaseException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.LANGUAGE_SET_SUCCESSFULLY)).build();
    }

}

