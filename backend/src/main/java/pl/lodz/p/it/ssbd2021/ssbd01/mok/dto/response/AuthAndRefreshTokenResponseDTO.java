package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Typ Auth and refresh token response dto - odpowiedź z loginem, rolami, tokenem i tokenem do odświeżania.
 */
public class AuthAndRefreshTokenResponseDTO {
    @NotNull
    private final JwtTokenResponseDto authJwtToken;
    @NotNull
    private final JwtTokenResponseDto refreshJwtToken;
    @NotNull
    private final String username;
    @NotNull
    private final Set<String> roles;

    /**
     * Tworzy nową instancję klasy.
     *
     * @param authJwtToken    auth jwt token
     * @param refreshJwtToken refresh jwt token
     * @param username        username
     * @param roles           roles
     */
    public AuthAndRefreshTokenResponseDTO(String authJwtToken, String refreshJwtToken, String username, Set<String> roles) {
        this.authJwtToken = new JwtTokenResponseDto(authJwtToken);
        this.refreshJwtToken = new JwtTokenResponseDto(refreshJwtToken);
        this.username = username;
        this.roles = roles;
    }

    /**
     * Pobiera pole username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Pobiera pole roles.
     *
     * @return roles
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * Pobiera pole auth jwt token.
     *
     * @return auth jwt token
     */
    public JwtTokenResponseDto getAuthJwtToken() {
        return authJwtToken;
    }

    /**
     * Pobiera pole refresh jwt token.
     *
     * @return refresh jwt token
     */
    public JwtTokenResponseDto getRefreshJwtToken() {
        return refreshJwtToken;
    }
}
