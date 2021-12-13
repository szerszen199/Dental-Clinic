package pl.lodz.p.it.ssbd2021.ssbd01.mok.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.auth.ejb.managers.AuthViewEntityManager;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.AuthenticationRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.RefreshTokenRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.AuthAndRefreshTokenResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.JwtTokenAndUserDataReponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.JwtTokenResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.UserInfoResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtLoginUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtRefreshUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtResetPasswordConfirmation;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.IpAddressUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.MailProvider;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_NOT_FOUND;

/**
 * Typ Login endpoint do logowania.
 */
@RequestScoped
@Path("auth")
@PermitAll
@Interceptors({LogInterceptor.class})
public class LoginEndpoint {

    private IdentityStoreHandler identityStoreHandler;
    private AccountManager accountManager;
    private AuthViewEntityManager authViewEntityManager;
    private HttpServletRequest request;
    private JwtLoginUtils jwtLoginUtils;
    private PropertiesLoader propertiesLoader;
    private JwtRefreshUtils jwtRefreshUtils;
    private MailProvider mailProvider;
    private JwtResetPasswordConfirmation jwtResetPasswordConfirmation;
    private LoggedInAccountUtil loggedInAccountUtil;


    /**
     * Tworzy nową instancję klasy Login endpoint.
     *
     * @param identityStoreHandler         identity store handler
     * @param jwtLoginUtils                jwt utils
     * @param httpServletRequest           http servlet request
     * @param accountManager               account manager
     * @param propertiesLoader             properties loader
     * @param jwtRefreshUtils              jwt refresh utils
     * @param mailProvider                 mail provider
     * @param authViewEntityManager        auth view entity manager
     * @param jwtResetPasswordConfirmation token do potwierdzenia resetu hasła

     */
    @Inject
    public LoginEndpoint(IdentityStoreHandler identityStoreHandler,
                         JwtLoginUtils jwtLoginUtils,
                         HttpServletRequest httpServletRequest,
                         AccountManager accountManager,
                         PropertiesLoader propertiesLoader,
                         JwtRefreshUtils jwtRefreshUtils,
                         MailProvider mailProvider,
                         AuthViewEntityManager authViewEntityManager,
                         JwtResetPasswordConfirmation jwtResetPasswordConfirmation,
                         LoggedInAccountUtil loggedInAccountUtil) {
        this.identityStoreHandler = identityStoreHandler;
        this.jwtLoginUtils = jwtLoginUtils;
        this.request = httpServletRequest;
        this.accountManager = accountManager;
        this.propertiesLoader = propertiesLoader;
        this.jwtRefreshUtils = jwtRefreshUtils;
        this.mailProvider = mailProvider;
        this.authViewEntityManager = authViewEntityManager;
        this.jwtResetPasswordConfirmation = jwtResetPasswordConfirmation;
        this.loggedInAccountUtil = loggedInAccountUtil;
    }

    public LoginEndpoint() {
    }

    /**
     * Pobiera pole refresh token.
     *
     * @param refreshTokenRequestDTO refresh token request dto
     * @return refresh token
     */
    @RolesAllowed({I18n.ADMIN, I18n.RECEPTIONIST, I18n.DOCTOR, I18n.PATIENT})
    @POST
    @Path("refresh")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewTokenForRefreshToken(@NotNull @Valid RefreshTokenRequestDTO refreshTokenRequestDTO) {
        String jwt = refreshTokenRequestDTO.getRefreshToken();
        if (jwtRefreshUtils.validateJwtToken(jwt)) {
            String username;
            Set<String> roleNames;
            try {
                username = jwtRefreshUtils.getUserNameFromJwtToken(jwt);
                roleNames = new HashSet<>();

                for (var i : authViewEntityManager.findByLogin(username)) {
                    roleNames.add(i.getLevel());
                }
            } catch (AccountException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_NOT_FOUND)).build();
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.INVALID_REFRESH_TOKEN)).build();
            }

            return Response.ok()
                    .entity(new AuthAndRefreshTokenResponseDTO(
                            jwtLoginUtils.generateJwtTokenForUser(username),
                            "eyJhbGciOiJIUzM4NCJ9.eyJleHAiOjIxMTI4MTI4MzksInN1YiI6ImlQaG9uZSJ9.whfhcXAD0f6MRI8VBX_JFhRUo27KyPkvY0e9QdXvJKKFtx79W3szKc2snRXEPvdK",
                            username,
                            roleNames))
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.INVALID_REFRESH_TOKEN)).build();
    }


    /**
     * Endpoint Logowania się na konto, metoda POST.
     *
     * @param authenticationRequestDTO DTO zawierające login i hasło konta
     * @return Odpowiedź, 401 jeśli nie udało się zalogować, 200 jeśli udało się zalogować.
     */
    @PermitAll
    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(@NotNull @Valid AuthenticationRequestDTO authenticationRequestDTO) {
        String ip = IpAddressUtils.getClientIpAddressFromHttpServletRequest(request);
        CredentialValidationResult credentialValidationResult = identityStoreHandler.validate(authenticationRequestDTO.toCredential());

        Account account;
        try {
            account = accountManager.findByLogin(authenticationRequestDTO.getUsername());
        } catch (AccountException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_NOT_FOUND)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.AUTHENTICATION_FAILURE)).build();
        }
        if (credentialValidationResult.getStatus() != CredentialValidationResult.Status.VALID) {
            try {
                accountManager.updateAfterUnsuccessfulLogin(authenticationRequestDTO.getUsername(), ip, LocalDateTime.now());
            } catch (AccountException accountException) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(accountException.getMessage())).build();
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.LOGIN_FAILURE)).build();
            }
            if (account.getUnsuccessfulLoginCounter() >= propertiesLoader.getInvalidLoginCountBlock() && account.getActive()) {
                try {
                    accountManager.lockAccount(account.getLogin());
                    mailProvider.sendAccountLockByUnsuccessfulLoginMail(account.getEmail(), account.getLanguage());
                } catch (AccountException accountException) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(accountException.getMessage())).build();
                } catch (Exception e) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.LOGIN_FAILURE)).build();
                }
            }
            Logger.getGlobal().log(Level.INFO, "Nieudana próba logowania na konto {0} z adresu {1}", new Object[]{account.getLogin(), ip});

            return Response.status(Response.Status.UNAUTHORIZED).entity(new MessageResponseDto(I18n.LOGIN_FAILURE)).build();
        }
        try {
            Account checkedAccount = accountManager.findByLogin(authenticationRequestDTO.getUsername());
            if (!checkedAccount.getFirstPasswordChange()) {
                String token = jwtResetPasswordConfirmation.generateJwtTokenForUsername(checkedAccount.getLogin());
                return Response.status(210).entity(new JwtTokenResponseDto(token)).build();
            }
        } catch (AccountException accountException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(accountException.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.LOGIN_FAILURE)).build();
        }

        UserInfoResponseDTO userInfoResponseDTO = new UserInfoResponseDTO();
        try {
            accountManager.updateAfterSuccessfulLogin(credentialValidationResult.getCallerPrincipal().getName(), ip, LocalDateTime.now());
            Account loggedInAccount = accountManager.findByLogin(authenticationRequestDTO.getUsername());
            userInfoResponseDTO.setFirstName(loggedInAccount.getFirstName());
            userInfoResponseDTO.setLastName(loggedInAccount.getLastName());
            userInfoResponseDTO.setDarkMode(loggedInAccount.isDarkMode());
            userInfoResponseDTO.setLanguage(loggedInAccount.getLanguage());
        } catch (AccountException accountException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(accountException.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.LOGIN_FAILURE)).build();
        }

        try {
            if (credentialValidationResult.getStatus() == CredentialValidationResult.Status.VALID && credentialValidationResult.getCallerGroups().contains(I18n.ADMIN)) {
                mailProvider.sendAdminLoginMail(accountManager.findByLogin(authenticationRequestDTO.getUsername()).getEmail(), account.getLanguage(), ip);
            }
        } catch (MailSendingException | AccountException accountException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(accountException.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.LOGIN_FAILURE)).build();
        }


        Logger.getGlobal().log(Level.INFO, "Zalogowano na konto {0} z adresu {1}", new Object[]{credentialValidationResult.getCallerPrincipal().getName(), ip});
        return Response.ok().entity(
                new JwtTokenAndUserDataReponseDTO(credentialValidationResult.getCallerPrincipal().getName(),
                        credentialValidationResult.getCallerGroups(),
                        jwtLoginUtils.generateJwtTokenForUser(credentialValidationResult.getCallerPrincipal().getName()),
                        jwtRefreshUtils.generateJwtTokenForUser(credentialValidationResult.getCallerPrincipal().getName()),
                        userInfoResponseDTO)).build();
    }


    /**
     * Endpoint sprawdzający, czy zmiana poziomu dostępu jest możliwa.
     *
     * @param level pożądany poziom dostępu
     * @return Odpowiedź http 200 gdy zmiana jest możliwa, 401 gdy nie
     */
    @POST
    @Path("/change-level/{level}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({I18n.ADMIN, I18n.RECEPTIONIST, I18n.DOCTOR, I18n.PATIENT})
    public Response changeRole(@PathParam("level") String level) {
        if (loggedInAccountUtil.isCallerInRole(level)) {
            Logger.getGlobal().log(Level.INFO, "Użytkownik o loginie {0} zmienił poziom dostępu na {1}", new Object[]{loggedInAccountUtil.getLoggedInAccountLogin(), level});
            return Response.ok().build();
        } else {
            Logger.getGlobal().log(Level.WARNING, "Użytkownik o loginie {0} próbował zmienić poziom dostępu na {1}", new Object[]{loggedInAccountUtil.getLoggedInAccountLogin(), level});
            return Response.status(UNAUTHORIZED).entity(new MessageResponseDto(I18n.NO_ACCESS_LEVEL)).build();
        }
    }

}

