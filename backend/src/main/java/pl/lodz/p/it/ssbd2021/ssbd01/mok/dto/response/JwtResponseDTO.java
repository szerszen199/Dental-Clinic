package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response;

import java.util.Set;

/**
 * Dto dla odpowiedzi z tokenem Jwt przy logowaniu.
 */
public class JwtResponseDTO {
    private final String username;
    private final Set<String> roles;
    private final String token;
    private final UserInfoResponseDTO userInfoResponseDTO;

    /**
     * Tworzy nową instancję klasy.
     *  @param username username
     * @param roles    roles
     * @param token    token
     * @param userInfoResponseDTO informacje o użytkowniku
     */
    public JwtResponseDTO(String username, Set<String> roles, String token, UserInfoResponseDTO userInfoResponseDTO) {
        this.username = username;
        this.roles = roles;
        this.token = token;
        this.userInfoResponseDTO = userInfoResponseDTO;
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
     * Pobiera pole token.
     *
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * Pobiera pole user info response dto.
     *
     * @return user info response dto
     */
    public UserInfoResponseDTO getUserInfoResponseDTO() {
        return userInfoResponseDTO;
    }
}
