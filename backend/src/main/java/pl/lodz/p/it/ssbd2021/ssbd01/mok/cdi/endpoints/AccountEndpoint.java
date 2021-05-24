package pl.lodz.p.it.ssbd2021.ssbd01.mok.cdi.endpoints;


import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.ChangePasswordRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.ConfirmAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.ConfirmMailChangeRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.CreateAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.EditAnotherAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.EditOwnAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.RevokeAndGrantAccessLevelDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.SetDarkModeRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.SetLanguageRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.SimpleUsernameRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.AccountInfoResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccessLevelManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtResetPasswordConfirmation;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignatureFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.MailProvider;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.converters.AccountConverter;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
    @Inject
    private JwtResetPasswordConfirmation jwtResetPasswordConfirmation;

    @Context
    private HttpHeaders httpHeaders;

    @Inject
    private EntityIdentitySignerVerifier signer;


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
        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                this.accountManager.createAccount(
                        AccountConverter.createAccountEntityFromDto(accountDto),
                        servletContext
                );
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }

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
        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                this.accountManager.confirmAccountByToken(confirmAccountRequestDTO.getConfirmToken());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_CONFIRMED_SUCCESSFULLY)).build();
    }

    /**
     * Reset password.
     *
     * @param confirmAccountRequestDTO the confirm account request dto
     * @return the response
     * @throws AppBaseException the app base exception
     */
    @PUT
    @Path("reset")
    @PermitAll
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response resetPassword(@NotNull @Valid ConfirmAccountRequestDTO confirmAccountRequestDTO) throws AppBaseException {
        try {
            String username = jwtResetPasswordConfirmation.getUserNameFromJwtToken(confirmAccountRequestDTO.getConfirmToken());
            if (!jwtResetPasswordConfirmation.validateJwtToken(confirmAccountRequestDTO.getConfirmToken())) {
                throw AccountException.invalidConfirmationToken();
            }
            int retryTXCounter = 3;
            boolean rollbackTX = false;
            do {
                try {
                    this.accountManager.resetPassword(username, username);
                    rollbackTX = accountManager.isLastTransactionRollback();
                } catch (AppBaseException | EJBTransactionRolledbackException e) {
                    rollbackTX = true;
                }
            } while (rollbackTX && --retryTXCounter > 0);
            if (rollbackTX) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
            }
        } catch (ParseException e) {
            // TODO: 24.05.2021 Response
            e.printStackTrace();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.PASSWORD_RESET_SUCCESSFULLY)).build();
    }


    /**
     * Edit account data.
     *
     * @param accountDto     DTO edytowanego konta
     * @param header         nagłówek If-Match
     * @param servletContext kontekst serwletów, służy do współdzielenia informacji w ramach aplikacji
     * @return response
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/edit
    @POST
    @Path("edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.ADMIN, I18n.PATIENT})
    @SignatureFilterBinding
    @Produces({MediaType.APPLICATION_JSON})
    public Response editAccount(@NotNull @Valid EditOwnAccountRequestDTO accountDto, @HeaderParam("If-Match") String header, @Context ServletContext servletContext) throws AppBaseException {
        if (!signer.verifyEntityIntegrity(header, accountDto)) {
            throw AppBaseException.optimisticLockError();
        }
        // TODO: 21.05.2021 Obsługa wyjątków
        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                this.accountManager.editOwnAccount(accountDto, servletContext);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_EDITED_SUCCESSFULLY)).build();
    }


    /**
     * Edycja konta innego użytkownika.
     *
     * @param accountDto     DTO edytowanego konta
     * @param servletContext kontekst serwletów, służy do współdzielenia informacji w ramach aplikacji
     * @param header         nagłówek If-Match
     * @return response
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/edit/other
    @POST
    @Path("edit-other")
    @RolesAllowed({I18n.ADMIN})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @SignatureFilterBinding
    public Response editOtherAccount(@NotNull @Valid EditAnotherAccountRequestDTO accountDto, @HeaderParam("If-Match") String header, @Context ServletContext servletContext) throws AppBaseException {
        if (!signer.verifyEntityIntegrity(header, accountDto)) {
            throw AppBaseException.optimisticLockError();
        }
        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                accountManager.editOtherAccount(accountDto, servletContext);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }
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

        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                accountManager.confirmMailChangeByToken(confirmMailChangeRequestDTO.getToken());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }
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
        accessLevelManager.revokeAccessLevel(revokeAndGrantAccessLevelDTO.getLogin(), revokeAndGrantAccessLevelDTO.getLevel());
        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                accessLevelManager.revokeAccessLevel(revokeAndGrantAccessLevelDTO.getLogin(), revokeAndGrantAccessLevelDTO.getLevel());
                rollbackTX = accessLevelManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }
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
        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                accountManager.lockAccount(simpleUsernameRequestDTO.getLogin());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }
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
        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                accountManager.unlockAccount(simpleUsernameRequestDTO.getLogin());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }
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
        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                accessLevelManager.addAccessLevel(revokeAndGrantAccessLevelDTO.getLogin(), revokeAndGrantAccessLevelDTO.getLevel());
                rollbackTX = accessLevelManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }
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
        return Response.ok().entity(account).tag(signer.sign(account)).build();
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

        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                accountManager.changePassword(
                        loggedInAccountUtil.getLoggedInAccountLogin(),
                        newPassword.getOldPassword(),
                        newPassword.getFirstPassword()
                );
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.PASSWORD_CHANGED_SUCCESSFULLY)).build();
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
    @Path("reset-other-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resetOthersPassword(@NotNull @Valid SimpleUsernameRequestDTO simpleUsernameRequestDTO) throws AppBaseException {
        // TODO: 21.05.2021 Obsługa wyjątków.
        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                accountManager.resetPassword(simpleUsernameRequestDTO.getLogin(), loggedInAccountUtil.getLoggedInAccountLogin());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.PASSWORD_RESET_SUCCESSFULLY)).build();
    }

    /**
     * Resetuje hasło użytkownikowi.
     *
     * @param simpleUsernameRequestDTO the simple username request dto
     * @param servletContext           the servlet context
     * @return odpowiedź na żądanie
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    @PUT
    @Path("reset-password")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resetOwnPassword(@NotNull @Valid SimpleUsernameRequestDTO simpleUsernameRequestDTO, @Context ServletContext servletContext) throws AppBaseException {
        // TODO: 21.05.2021  Ob słu ga Wy jąt ków
        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                accountManager.sendResetPasswordConfirmationEmail(simpleUsernameRequestDTO.getLogin(), servletContext);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.PASSWORD_RESET_MAIL_SENT_SUCCESSFULLY)).build();
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
        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                accountManager.setDarkMode(login, setDarkModeRequestDTO.isDarkMode());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
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
        int retryTXCounter = 3;
        boolean rollbackTX = false;
        do {
            try {
                accountManager.setLanguage(login, setLanguageRequestDTO.getLanguage().toLowerCase(Locale.ROOT));
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.LANGUAGE_SET_SUCCESSFULLY)).build();
    }

}

