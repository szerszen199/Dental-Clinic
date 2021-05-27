package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;

/**
 * Typ Refresh token request dto.
 */
public class RefreshTokenRequestDTO {

    @NotNull(message = I18n.TOKEN_NULL)
    private String refreshToken;

    /**
     * Pobiera pole refresh token.
     *
     * @return refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Ustawia pole refresh token.
     *
     * @param refreshToken refresh token
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Tworzy nową instancję klasy Refresh token request dto.
     */
    public RefreshTokenRequestDTO() {
    }

    /**
     * Tworzy nową instancję klasy Refresh token request dto.
     *
     * @param refreshToken refresh token
     */
    public RefreshTokenRequestDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
