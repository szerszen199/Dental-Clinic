package pl.lodz.p.it.ssbd2021.ssbd01.mok.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.JwtResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.LoginRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtLoginUtils;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Typ Login endpoint.
 */
@Path("login")
@PermitAll
public class LoginEndpoint {

    private final IdentityStoreHandler identityStoreHandler;

    private final JwtLoginUtils jwtUtils;

    /**
     * Tworzy nową instancję klasy Login endpoint.
     *
     * @param identityStoreHandler identity store handler
     * @param jwtUtils             jwt utils
     */
    @Inject
    public LoginEndpoint(IdentityStoreHandler identityStoreHandler, JwtLoginUtils jwtUtils) {
        this.identityStoreHandler = identityStoreHandler;
        this.jwtUtils = jwtUtils;
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
        CredentialValidationResult credentialValidationResult = identityStoreHandler.validate(loginRequestDTO.toCredential());
        if (credentialValidationResult.getStatus() != CredentialValidationResult.Status.VALID) {
            // TODO: 09.05.2021 Poprawić odpowiedź
            return Response.status(Response.Status.UNAUTHORIZED).entity(new MessageResponseDto("Invalid Login Password Combination")).build();
        }
        return Response.ok().entity(
                new JwtResponseDTO(credentialValidationResult.getCallerPrincipal().getName(),
                        credentialValidationResult.getCallerGroups(),
                        jwtUtils.generateJwtTokenForUser(credentialValidationResult.getCallerPrincipal().getName()))).build();
    }

}
