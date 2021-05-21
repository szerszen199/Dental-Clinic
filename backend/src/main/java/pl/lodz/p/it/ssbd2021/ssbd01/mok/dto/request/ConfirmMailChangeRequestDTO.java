package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;

import javax.validation.constraints.NotNull;

/**
 * Typ Confirm mail change request dto.
 */
public class ConfirmMailChangeRequestDTO {
    @NotNull
    private String token;

    /**
     * Pobiera pole token.
     *
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * Ustawia pole token.
     *
     * @param token token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Tworzy nową instancję klasy Confirm mail change request dto.
     */
    public ConfirmMailChangeRequestDTO() {
    }
}
