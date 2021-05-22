package pl.lodz.p.it.ssbd2021.ssbd01.mok.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.auth.ejb.managers.AuthViewEntityManager;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.LoginRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.RefreshTokenRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.AuthAndRefreshTokenResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.JwtTokenAndUserDataReponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.AuthenticationRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.UserInfoResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtLoginUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtRefreshUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.MailProvider;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Typ Login endpoint.
 */
@Path("auth")
@PermitAll
@Interceptors({LogInterceptor.class})
public class LoginEndpoint {

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"};
    private final IdentityStoreHandler identityStoreHandler;
    private final AccountManager accountManager;
    private final AuthViewEntityManager authViewEntityManager;
    private final HttpServletRequest request;
    private final JwtLoginUtils jwtLoginUtils;
    private final PropertiesLoader propertiesLoader;
    private final JwtRefreshUtils jwtRefreshUtils;
    private final MailProvider mailProvider;


    /**
     * Tworzy nową instancję klasy Login endpoint.
     *
     * @param identityStoreHandler  identity store handler
     * @param jwtLoginUtils         jwt utils
     * @param httpServletRequest    http servlet request
     * @param accountManager        account manager
     * @param propertiesLoader      properties loader
     * @param jwtRefreshUtils       jwt refresh utils
     * @param mailProvider          mail provider
     * @param authViewEntityManager auth view entity manager
     */
    @Inject
    public LoginEndpoint(IdentityStoreHandler identityStoreHandler,
                         JwtLoginUtils jwtLoginUtils,
                         HttpServletRequest httpServletRequest,
                         AccountManager accountManager,
                         PropertiesLoader propertiesLoader,
                         JwtRefreshUtils jwtRefreshUtils,
                         MailProvider mailProvider,
                         AuthViewEntityManager authViewEntityManager) {
        this.identityStoreHandler = identityStoreHandler;
        this.jwtLoginUtils = jwtLoginUtils;
        this.request = httpServletRequest;
        this.accountManager = accountManager;
        this.propertiesLoader = propertiesLoader;
        this.jwtRefreshUtils = jwtRefreshUtils;
        this.mailProvider = mailProvider;
        this.authViewEntityManager = authViewEntityManager;
    }

    /**
     * Pobiera pole refresh token.
     *
     * @param refreshTokenRequestDTO refresh token request dto
     * @return refresh token
     */
    // TODO: 21.05.2021
    @RolesAllowed({I18n.ADMIN, I18n.RECEPTIONIST, I18n.DOCTOR, I18n.PATIENT})
    @POST
    @Path("refresh")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewTokenForRefreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        String jwt = refreshTokenRequestDTO.getRefreshToken();
        if (jwtRefreshUtils.validateJwtToken(jwt)) {
            try {
                String username = jwtRefreshUtils.getUserNameFromJwtToken(jwt);
                Set<String> roleNames = new HashSet<>();
                for (var i : authViewEntityManager.findByLogin(username)) {
                    roleNames.add(i.getLevel());
                }
                return Response.ok()
                        .entity(new AuthAndRefreshTokenResponseDTO(
                                jwtLoginUtils.generateJwtTokenForUser(username),
                                jwtRefreshUtils.generateJwtTokenForUser(username), username, roleNames))
                        .build();
            } catch (ParseException | AppBaseException e) {
                e.printStackTrace();
            }
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
    public Response authenticate(AuthenticationRequestDTO authenticationRequestDTO) {
        String ip = getClientIpAddress(request);
        CredentialValidationResult credentialValidationResult = identityStoreHandler.validate(authenticationRequestDTO.toCredential());
        try {
            Account account = accountManager.findByLogin(authenticationRequestDTO.getUsername());
            if (credentialValidationResult.getStatus() != CredentialValidationResult.Status.VALID) {
                accountManager.updateAfterUnsuccessfulLogin(authenticationRequestDTO.getUsername(), ip, LocalDateTime.now());
                if (account.getUnsuccessfulLoginCounter() >= propertiesLoader.getInvalidLoginCountBlock() && account.getActive()) {
                    accountManager.lockAccount(account.getLogin());
                    // TODO: 11.05.2021 informacja na maila? Idk
                }
                Logger.getGlobal().log(Level.INFO, "Nieudana próba logowania na konto {0} z adresu {1}", new Object[]{account.getLogin(), getClientIpAddress(request)});
                return Response.status(Response.Status.UNAUTHORIZED).entity(new MessageResponseDto(I18n.AUTHENTICATION_FAILURE)).build();
            }
        } catch (AppBaseException e) {
            // TODO: 11.05.2021 Moze tutaj cos zrobic?
            e.printStackTrace();
        }
        UserInfoResponseDTO userInfoResponseDTO = new UserInfoResponseDTO();
        try {
            accountManager.updateAfterSuccessfulLogin(credentialValidationResult.getCallerPrincipal().getName(), ip, LocalDateTime.now());
            Account loggedInAccount = accountManager.findByLogin(authenticationRequestDTO.getUsername());
            userInfoResponseDTO.setFirstName(loggedInAccount.getFirstName());
            userInfoResponseDTO.setLastName(loggedInAccount.getLastName());
            userInfoResponseDTO.setDarkMode(loggedInAccount.isDarkMode());
            userInfoResponseDTO.setLanguage(loggedInAccount.getLanguage());
        } catch (AppBaseException e) {
            // TODO: 11.05.2021 Moze tutaj cos zrobic?
            e.printStackTrace();
        }
        try {
            if (credentialValidationResult.getStatus() == CredentialValidationResult.Status.VALID && credentialValidationResult.getCallerGroups().contains(I18n.ADMIN)) {
                mailProvider.sendAdminLoginMail(accountManager.findByLogin(authenticationRequestDTO.getUsername()).getEmail());
            }
        } catch (AppBaseException e) {
            e.printStackTrace();
        }

        Logger.getGlobal().log(Level.INFO, "Zalogowano na konto {0} z adresu {1}", new Object[]{credentialValidationResult.getCallerPrincipal().getName(), getClientIpAddress(request)});
        return Response.ok().entity(
                new JwtTokenAndUserDataReponseDTO(credentialValidationResult.getCallerPrincipal().getName(),
                        credentialValidationResult.getCallerGroups(),
                        jwtLoginUtils.generateJwtTokenForUser(credentialValidationResult.getCallerPrincipal().getName()),
                        jwtRefreshUtils.generateJwtTokenForUser(credentialValidationResult.getCallerPrincipal().getName()),
                        userInfoResponseDTO)).build();
    }


    private String getClientIpAddress(HttpServletRequest request) {
        for (var header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }

        return request.getRemoteAddr();
    }
}
