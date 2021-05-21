package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response;

import java.util.Set;

/**
 * Dto dla odpowiedzi z tokenem Jwt przy logowaniu.
 */
public class JwtTokenAndUserDataReponseDTO extends AuthAndRefreshTokenResponseDTO {
    private final String username;
    private final Set<String> roles;
    private final UserInfoResponseDTO userInfoResponseDTO;

    /**
     * Tworzy nową instancję klasy.
     *
     * @param username            username
     * @param roles               roles
     * @param authJwtToken        auth jwt token
     * @param refreshJwtToken     refresh jwt token
     * @param userInfoResponseDTO informacje o użytkowniku
     */
    public JwtTokenAndUserDataReponseDTO(String username, Set<String> roles, String authJwtToken, String refreshJwtToken, UserInfoResponseDTO userInfoResponseDTO) {
        super(authJwtToken, refreshJwtToken);
        this.username = username;
        this.roles = roles;
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
     * Pobiera pole user info response dto.
     *
     * @return user info response dto
     */
    public UserInfoResponseDTO getUserInfoResponseDTO() {
        return userInfoResponseDTO;
    }
}
