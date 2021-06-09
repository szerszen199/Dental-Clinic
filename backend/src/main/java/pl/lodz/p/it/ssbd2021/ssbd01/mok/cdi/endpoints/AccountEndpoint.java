package pl.lodz.p.it.ssbd2021.ssbd01.mok.cdi.endpoints;


import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccessLevelException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.ChangePasswordRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.ConfirmAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.ConfirmMailChangeRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.CreateAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.EditAnotherAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.EditOwnAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.RevokeAndGrantAccessLevelDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.SetDarkModeRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.SetLanguageRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.SetNewPasswordRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.SimpleUsernameRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.AccountInfoResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.AccountInfoWithAccessLevelsResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccessLevelManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtResetPasswordConfirmation;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignatureFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.MailProvider;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.converters.AccountConverter;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCESS_LEVEL_ADD_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCESS_LEVEL_REVOKED_SUCCESSFULLY;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCESS_LEVEL_REVOKE_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_CONFIRMATION_BY_TOKEN_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_CREATION_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_DARK_MODE_SET_SUCCESSFULLY;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_EDIT_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_GET_ALL_ACCOUNTS_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_GET_LOGGED_IN_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_GET_WITH_LOGIN_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_LOCKED_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_NOT_FOUND;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_SET_DARK_MODE_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_SET_LANGUAGE_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_UNLOCKED_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.DATABASE_OPTIMISTIC_LOCK_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.EMAIL_CONFIRMATION_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.PASSWORD_CHANGE_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.PASSWORD_RESET_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.TRANSACTION_FAILED_ERROR;

/**
 * Typ Account endpoint - dal konta.
 */
@Path("account")
@Stateful
@Interceptors(LogInterceptor.class)
public class AccountEndpoint {

    @Inject
    private PropertiesLoader propertiesLoader;

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
     * @param accountDto obiekt zawierający login, email, hasło i inne wymagane dane
     * @return response
     */
    @POST
    @PermitAll
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createAccount(@NotNull @Valid CreateAccountRequestDTO accountDto) {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                this.accountManager.createAccount(
                        AccountConverter.createAccountEntityFromDto(accountDto)
                );
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (exception != null) {
            if ((exception instanceof AccountException || exception instanceof MailSendingException)) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            }
            if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_CREATED_SUCCESSFULLY)).build();
    }

    /**
     * Potwierdza konto.
     *
     * @param confirmAccountRequestDTO confirm account request dto
     * @return response
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/confirm?token={token}
    @PUT
    @Path("confirm")
    @PermitAll
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response confirmAccount(@NotNull @Valid ConfirmAccountRequestDTO confirmAccountRequestDTO) {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                this.accountManager.confirmAccountByToken(confirmAccountRequestDTO.getConfirmToken());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CONFIRMATION_BY_TOKEN_FAILED)).build();
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {
            if (exception instanceof AccountException || exception instanceof MailSendingException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CONFIRMATION_BY_TOKEN_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_CONFIRMED_SUCCESSFULLY)).build();
    }

    /**
     * Resetuje hasło.
     *
     * @param confirmAccountRequestDTO the confirm account request dto
     * @return response
     */
    @PUT
    @Path("reset")
    @PermitAll
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response resetPassword(@NotNull @Valid ConfirmAccountRequestDTO confirmAccountRequestDTO) {
        String username;
        Long version;
        try {
            String[] tokenData = jwtResetPasswordConfirmation.getVersionAndNameFromJwtToken(confirmAccountRequestDTO.getConfirmToken()).split("/");
            username = tokenData[0];
            version = Long.valueOf(tokenData[1]);
            if (!jwtResetPasswordConfirmation.validateJwtToken(confirmAccountRequestDTO.getConfirmToken())) {
                throw AccountException.invalidConfirmationToken();
            }
            Account account = accountManager.findByLogin(username);
            if (!account.getVersion().equals(version)) {
                throw AccountException.passwordAlreadyChanged();
            }
        } catch (AccountException accountException) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(accountException.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(PASSWORD_RESET_FAILED)).build();
        }
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                this.accountManager.resetPassword(username, username);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {
            if (exception instanceof AccountException || exception instanceof MailSendingException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_EDIT_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.PASSWORD_RESET_SUCCESSFULLY)).build();
    }

    /**
     * Ustawia nowe hasło.
     *
     * @param setNewPasswordRequestDTO the setNewPasswordRequestDTO
     * @return response
     */
    @PUT
    @Path("set-new-password")
    @PermitAll
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response setNewPassword(@NotNull @Valid SetNewPasswordRequestDTO setNewPasswordRequestDTO) {
        String username;
        Long version;
        try {
            String[] tokenData = jwtResetPasswordConfirmation.getUserNameFromJwtToken(setNewPasswordRequestDTO.getConfirmToken()).split("/");
            username = tokenData[0];
            version = Long.valueOf(tokenData[1]);
            if (!jwtResetPasswordConfirmation.validateJwtToken(setNewPasswordRequestDTO.getConfirmToken())) {
                throw AccountException.invalidConfirmationToken();
            }
            Account account = accountManager.findByLogin(username);
            if (!account.getVersion().equals(version)) {
                throw AccountException.passwordAlreadyChanged();
            }
        } catch (AccountException accountException) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(accountException.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(PASSWORD_RESET_FAILED)).build();
        }
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                this.accountManager.setNewPassword(username, setNewPasswordRequestDTO.getFirstPassword());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {
            if (exception instanceof AccountException || exception instanceof MailSendingException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_EDIT_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.PASSWORD_RESET_SUCCESSFULLY)).build();
    }

    /**
     * Odblokowanie poprzez maila po automatycznym zablokowaniu konta.
     *
     * @param confirmMailChangeRequestDTO the confirm mail change request dto
     * @return response response
     */
    @PUT
    @Path("unlock-by-mail")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response unlockByMail(@NotNull @Valid ConfirmMailChangeRequestDTO confirmMailChangeRequestDTO) {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                accountManager.confirmUnlockByToken(confirmMailChangeRequestDTO.getToken());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        if (exception != null && (exception instanceof AccountException || exception instanceof MailSendingException)) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
        } else if (exception != null && exception instanceof AppBaseException) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.CONFIRM_BY_MAIL_FAILED)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.CONFIRM_BY_MAIL_SUCCESSFULLY)).build();
    }

    /**
     * Edycja danych konta.
     *
     * @param accountDto DTO edytowanego konta
     * @param header     nagłówek If-Match
     * @return response
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/edit
    @POST
    @Path("edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.ADMIN, I18n.PATIENT})
    @SignatureFilterBinding
    @Produces({MediaType.APPLICATION_JSON})
    public Response editAccount(@NotNull @Valid EditOwnAccountRequestDTO accountDto, @HeaderParam("If-Match") String header) {
        if (!signer.verifyEntityIntegrity(header, accountDto)) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(DATABASE_OPTIMISTIC_LOCK_ERROR)).build();
        }
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                this.accountManager.editOwnAccount(accountDto);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {
            if (exception instanceof AccountException || exception instanceof MailSendingException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_EDIT_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_EDITED_SUCCESSFULLY)).build();
    }


    /**
     * Edycja konta innego użytkownika.
     *
     * @param accountDto DTO edytowanego konta
     * @param header     nagłówek If-Match
     * @return response
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/edit/other
    @POST
    @Path("edit-other")
    @RolesAllowed({I18n.ADMIN})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @SignatureFilterBinding
    public Response editOtherAccount(@NotNull @Valid EditAnotherAccountRequestDTO accountDto, @HeaderParam("If-Match") String header) {
        if (!signer.verifyEntityIntegrity(header, accountDto)) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(DATABASE_OPTIMISTIC_LOCK_ERROR)).build();
        }
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                accountManager.editOtherAccount(accountDto);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {
            if (exception instanceof AccountException || exception instanceof MailSendingException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_EDIT_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_EDITED_SUCCESSFULLY)).build();
    }

    /**
     * Endpoint potwierdzający zmianę maila.
     *
     * @param confirmMailChangeRequestDTO confirm mail change request dto
     * @return response
     */
    @PUT
    @Path("confirm-mail")
    // Rozumiem że to nie wymaga zalogowania
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response confirmMailChange(@NotNull @Valid ConfirmMailChangeRequestDTO confirmMailChangeRequestDTO) {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                accountManager.confirmMailChangeByToken(confirmMailChangeRequestDTO.getToken());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {
            if (exception instanceof AccountException || exception instanceof MailSendingException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(EMAIL_CONFIRMATION_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.EMAIL_CONFIRMED_SUCCESSFULLY)).build();
    }


    /**
     * Metoda służąca do blokowania konta przez administratora.
     *
     * @param simpleUsernameRequestDTO simple username request dto
     * @return response
     */
    @PUT
    @RolesAllowed({I18n.ADMIN})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("lock")
    @Produces({MediaType.APPLICATION_JSON})
    public Response lockAccount(@NotNull @Valid SimpleUsernameRequestDTO simpleUsernameRequestDTO) {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                accountManager.lockAccount(simpleUsernameRequestDTO.getLogin());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {
            if (exception instanceof AccountException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_LOCKED_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        try {
            Account account = accountManager.findByLogin(simpleUsernameRequestDTO.getLogin());
            mailProvider.sendAccountLockByAdminMail(account.getEmail(), account.getLanguage());
        } catch (AccountException | MailSendingException accountException) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(accountException.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_LOCKED_FAILED)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_LOCKED_SUCCESSFULLY)).build();
    }

    /**
     * Metoda służąca do odblokowywania konta przez administratora.
     *
     * @param simpleUsernameRequestDTO simple username request dto
     * @return response
     */
    @PUT
    @RolesAllowed({I18n.ADMIN})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("unlock")
    @Produces({MediaType.APPLICATION_JSON})
    public Response unlockAccount(@NotNull @Valid SimpleUsernameRequestDTO simpleUsernameRequestDTO) {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                accountManager.unlockAccount(simpleUsernameRequestDTO.getLogin());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = null;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {

            if (exception instanceof AccountException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_UNLOCKED_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();

        }
        try {
            Account account = accountManager.findByLogin(simpleUsernameRequestDTO.getLogin());
            mailProvider.sendAccountUnlockByAdminMail(account.getEmail(), account.getLanguage());
        } catch (MailSendingException accountException) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(accountException.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_UNLOCKED_FAILED)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_UNLOCKED_SUCCESSFULLY)).build();
    }

    /**
     * Dodanie poziomu dostępu {@param level} dla użytkownika o {@param login}.
     *
     * @param revokeAndGrantAccessLevelDTO revoke and grant access level dto
     * @return odpowiedź 400 gdy administrator próbuje sam sobie dodać poziom dostępu, 200 gdy dodanie poprawne
     */
    @PUT
    @RolesAllowed({I18n.ADMIN})
    @Path("/addLevelByLogin")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addAccessLevel(@NotNull @Valid RevokeAndGrantAccessLevelDTO revokeAndGrantAccessLevelDTO) {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        if (revokeAndGrantAccessLevelDTO.getLogin().equals(loggedInAccountUtil.getLoggedInAccountLogin())) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCESS_LEVEL_ADD_FAILED)).build();
        }
        do {
            try {
                exception = null;
                accessLevelManager.addAccessLevel(revokeAndGrantAccessLevelDTO.getLogin(), revokeAndGrantAccessLevelDTO.getLevel());
                rollbackTX = accessLevelManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {
            if (exception instanceof AccountException || exception instanceof AccessLevelException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCESS_LEVEL_ADD_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        try {
            Account account = accountManager.findByLogin(revokeAndGrantAccessLevelDTO.getLogin());
            mailProvider.sendAccountGrantAccessLevelMail(account.getEmail(), revokeAndGrantAccessLevelDTO.getLevel(), account.getLanguage());
        } catch (MailSendingException accountException) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(accountException.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCESS_LEVEL_ADD_FAILED)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.ACCESS_LEVEL_ADDED_SUCCESSFULLY)).build();
    }

    /**
     * Revoke access level - enpoint odbierający poziom dostępu.
     *
     * @param revokeAndGrantAccessLevelDTO obiekt zawierający poziom oraz login
     * @return odpowiedź 400 gdy administrator próbuje sam sobie odebrać poziom dostępu, 200 gdy dodanie poprawne
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/revokeAccessLevel/{login}/{level}
    @PUT
    @RolesAllowed({I18n.ADMIN})
    @Path("/revokeAccessLevel")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response revokeAccessLevel(@NotNull @Valid RevokeAndGrantAccessLevelDTO revokeAndGrantAccessLevelDTO) {
        if (revokeAndGrantAccessLevelDTO.getLogin().equals(loggedInAccountUtil.getLoggedInAccountLogin())) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.ACCESS_LEVEL_SELF_REVOKE)).build();
        }
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                accessLevelManager.revokeAccessLevel(revokeAndGrantAccessLevelDTO.getLogin(), revokeAndGrantAccessLevelDTO.getLevel());
                rollbackTX = accessLevelManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (exception != null) {
            if (exception instanceof AccountException || exception instanceof AccessLevelException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCESS_LEVEL_REVOKE_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }

        try {
            Account account = accountManager.findByLogin(revokeAndGrantAccessLevelDTO.getLogin());
            mailProvider.sendAccountRevokeAccessLevelMail(account.getEmail(), revokeAndGrantAccessLevelDTO.getLevel(), account.getLanguage());
        } catch (MailSendingException accountException) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(accountException.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCESS_LEVEL_REVOKE_FAILED)).build();
        }
        return Response.ok().entity(new MessageResponseDto(ACCESS_LEVEL_REVOKED_SUCCESSFULLY)).build();
    }

    /**
     * Pobiera informacje o zalogowanm koncie.
     *
     * @return informacje o zalogowanym koncie
     */
    @GET
    @RolesAllowed({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.ADMIN, I18n.PATIENT})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/info")
    public Response getLoggedInAccountInfo() {
        AccountInfoResponseDTO account;
        try {
            account = new AccountInfoResponseDTO(accountManager.findByLogin(loggedInAccountUtil.getLoggedInAccountLogin()));
        } catch (AccountException e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_NOT_FOUND)).build();
        } catch (AppBaseException e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_GET_LOGGED_IN_FAILED)).build();
        }
        return Response.ok().entity(account).tag(signer.sign(account)).build();
    }


    /**
     * Pobiera informacje o koncie o {@param login}.
     *
     * @param login login konta o którym chcemy pobrać informacje
     * @return informacje o zalogowanym koncie
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.ADMIN})
    @Path("/other-account-info/{login}")
    public Response getAccountInfoWithLogin(@NotNull @PathParam("login") String login) {
        AccountInfoWithAccessLevelsResponseDto account;
        try {
            account = new AccountInfoWithAccessLevelsResponseDto(accountManager.findByLogin(login));
        } catch (AccountException e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_NOT_FOUND)).build();
        } catch (AppBaseException e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_GET_WITH_LOGIN_FAILED)).build();
        }
        return Response.ok().entity(account).tag(signer.sign(account)).build();
    }

    /**
     * Pobiera listę wszystkich kont.
     *
     * @return lista wszystkich kont
     */
    @GET
    @RolesAllowed({I18n.ADMIN})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/accounts")
    public Response getAllAccounts() {
        List<AccountInfoResponseDTO> accountInfoResponseDTOList;
        try {
            accountInfoResponseDTOList = accountManager.getAllAccounts()
                    .stream()
                    .map(AccountInfoWithAccessLevelsResponseDto::new)
                    .collect(Collectors.toList());
        } catch (AccountException e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (AppBaseException e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_GET_ALL_ACCOUNTS_FAILED)).build();
        }
        return Response.ok(accountInfoResponseDTOList).build();
    }

    /**
     * Zmienia hasło do własnego konta.
     *
     * @param newPassword informacje uwierzytelniające o starym haśle i nowym, które ma zostać ustawione
     * @return odpowiedź na żądanie
     */
    @PUT
    @RolesAllowed({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.ADMIN, I18n.PATIENT})
    @Path("new-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeOwnPassword(@NotNull @Valid ChangePasswordRequestDTO newPassword) {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                accountManager.changePassword(
                        loggedInAccountUtil.getLoggedInAccountLogin(),
                        newPassword.getOldPassword(),
                        newPassword.getFirstPassword()
                );
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {
            if (exception instanceof AccountException || exception instanceof PasswordException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(PASSWORD_CHANGE_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.PASSWORD_CHANGED_SUCCESSFULLY)).build();
    }

    /**
     * Resetuje hasło innego użytkownika z poziomu konta administratora.
     *
     * @param simpleUsernameRequestDTO simple username request dto
     * @return odpowiedź na żądanie
     */
    @PUT
    @RolesAllowed({I18n.ADMIN})
    @Path("reset-other-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resetOthersPassword(@NotNull @Valid SimpleUsernameRequestDTO simpleUsernameRequestDTO) {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                accountManager.resetPassword(simpleUsernameRequestDTO.getLogin(), loggedInAccountUtil.getLoggedInAccountLogin());
                accountManager.sendResetPasswordByAdminConfirmationEmail(simpleUsernameRequestDTO.getLogin());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {
            if (exception instanceof AccountException || exception instanceof MailSendingException || exception instanceof PasswordException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(PASSWORD_RESET_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.PASSWORD_RESET_MAIL_SENT_SUCCESSFULLY)).build();
    }

    /**
     * Resetuje hasło użytkownikowi.
     *
     * @param simpleUsernameRequestDTO the simple username request dto
     * @return odpowiedź na żądanie
     */
    @PUT
    @Path("reset-password")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resetOwnPassword(@NotNull @Valid SimpleUsernameRequestDTO simpleUsernameRequestDTO) {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                accountManager.sendResetPasswordConfirmationEmail(simpleUsernameRequestDTO.getLogin());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {
            if (exception instanceof AccountException || exception instanceof MailSendingException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(PASSWORD_RESET_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeDarkMode(@NotNull @Valid SetDarkModeRequestDTO setDarkModeRequestDTO) {
        String login = loggedInAccountUtil.getLoggedInAccountLogin();
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                accountManager.setDarkMode(login, setDarkModeRequestDTO.getDarkMode());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {
            if (exception instanceof AccountException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_SET_DARK_MODE_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(ACCOUNT_DARK_MODE_SET_SUCCESSFULLY)).build();
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
        String login = loggedInAccountUtil.getLoggedInAccountLogin();
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                accountManager.setLanguage(login, setLanguageRequestDTO.getLanguage().toLowerCase(Locale.ROOT));
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (exception != null) {
            if (exception instanceof AccountException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_SET_LANGUAGE_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.LANGUAGE_SET_SUCCESSFULLY)).build();
    }

}

