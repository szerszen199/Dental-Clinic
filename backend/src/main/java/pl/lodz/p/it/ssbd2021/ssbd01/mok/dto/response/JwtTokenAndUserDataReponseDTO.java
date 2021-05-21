package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response;

import java.util.Set;

/**
 * Dto dla odpowiedzi z tokenem Jwt przy logowaniu.
 */
public class JwtTokenAndUserDataReponseDTO extends AuthAndRefreshTokenResponseDTO {
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
        super(authJwtToken, refreshJwtToken, username, roles);
        this.userInfoResponseDTO = userInfoResponseDTO;
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
