package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response;

import javax.validation.constraints.NotNull;

/**
 * Typ Auth and refresh token response dto.
 */
public class AuthAndRefreshTokenResponseDTO {
    @NotNull
    private final JwtTokenResponseDto authJwtToken;
    @NotNull
    private final JwtTokenResponseDto refreshJwtToken;

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

    /**
     * Tworzy nową instancję klasy.
     *
     * @param authJwtToken    auth jwt token
     * @param refreshJwtToken refresh jwt token
     */
    public AuthAndRefreshTokenResponseDTO(String authJwtToken, String refreshJwtToken) {
        this.authJwtToken = new JwtTokenResponseDto(authJwtToken);
        this.refreshJwtToken = new JwtTokenResponseDto(refreshJwtToken);
    }
}
