package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response;

import javax.validation.constraints.NotNull;

/**
 * Typ Jwt refresh response dto - odpoweidź z tokenem.
 */
public class JwtTokenResponseDto {
    @NotNull
    private final String token;

    /**
     * Tworzy nową instancję klasy Jwt refresh response dto.
     *
     * @param token token
     */
    public JwtTokenResponseDto(String token) {
        this.token = token;
    }

    /**
     * Pobiera pole token.
     *
     * @return token
     */
    public String getToken() {
        return token;
    }
}
