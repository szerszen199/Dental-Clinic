package pl.lodz.p.it.ssbd2021.ssbd01.mok.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.JwtResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.LoginRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.UserInfoResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtLoginUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

import javax.annotation.security.PermitAll;
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
import java.time.LocalDateTime;

/**
 * Typ Login endpoint.
 */
@Path("login")
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
    private final HttpServletRequest request;
    private final JwtLoginUtils jwtUtils;
    private final PropertiesLoader propertiesLoader;


    /**
     * Tworzy nową instancję klasy Login endpoint.
     *
     * @param identityStoreHandler identity store handler
     * @param jwtUtils             jwt utils
     * @param httpServletRequest   http servlet request
     * @param accountManager       account manager
     * @param propertiesLoader     properties loader
     */
    @Inject
    public LoginEndpoint(IdentityStoreHandler identityStoreHandler,
                         JwtLoginUtils jwtUtils,
                         HttpServletRequest httpServletRequest,
                         AccountManager accountManager,
                         PropertiesLoader propertiesLoader) {
        this.identityStoreHandler = identityStoreHandler;
        this.jwtUtils = jwtUtils;
        this.request = httpServletRequest;
        this.accountManager = accountManager;
        this.propertiesLoader = propertiesLoader;
    }

    /**
     * Endpoint Logowania się na konto, metoda POST.
     *
     * @param loginRequestDTO DTO zawierające login i hasło konta
     * @return Odpowiedź, 401 jeśli nie udało się zalogować, 200 jeśli udało się zalogować.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(LoginRequestDTO loginRequestDTO) {
        String ip = getClientIpAddress(request);
        CredentialValidationResult credentialValidationResult = identityStoreHandler.validate(loginRequestDTO.toCredential());
        try {
        Account account = accountManager.findByLogin(loginRequestDTO.getUsername());
        if (credentialValidationResult.getStatus() != CredentialValidationResult.Status.VALID) {
                accountManager.updateAfterUnsuccessfulLogin(loginRequestDTO.getUsername(), ip, LocalDateTime.now());
                if (account.getUnsuccessfulLoginCounter() >= propertiesLoader.getInvalidLoginCountBlock() && account.getActive()) {
                    accountManager.lockAccount(account.getId());
                    // TODO: 11.05.2021 informacja na maila? Idk
                }
            return Response.status(Response.Status.UNAUTHORIZED).entity(new MessageResponseDto(I18n.AUTHENTICATION_FAILURE)).build();
        }
        } catch (AppBaseException e) {
            // TODO: 11.05.2021 Moze tutaj cos zrobic?
            e.printStackTrace();
        }
        UserInfoResponseDTO userInfoResponseDTO = new UserInfoResponseDTO();
        try {
            accountManager.updateAfterSuccessfulLogin(credentialValidationResult.getCallerPrincipal().getName(), ip, LocalDateTime.now());

            Account loggedInAccount = accountManager.findByLogin(loginRequestDTO.getUsername());
            userInfoResponseDTO.setFirstName(loggedInAccount.getFirstName());
            userInfoResponseDTO.setLastName(loggedInAccount.getLastName());
            userInfoResponseDTO.setDarkMode(loggedInAccount.isDarkMode());
            userInfoResponseDTO.setLanguage(loggedInAccount.getLanguage());
        } catch (AppBaseException e) {
            // TODO: 11.05.2021 Moze tutaj cos zrobic?
            e.printStackTrace();
        }

        return Response.ok().entity(
                new JwtResponseDTO(credentialValidationResult.getCallerPrincipal().getName(),
                        credentialValidationResult.getCallerGroups(),
                        jwtUtils.generateJwtTokenForUser(credentialValidationResult.getCallerPrincipal().getName()),
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
