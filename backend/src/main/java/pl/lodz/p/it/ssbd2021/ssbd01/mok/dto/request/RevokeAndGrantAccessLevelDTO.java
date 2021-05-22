package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.common.AccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.NotNull;

/**
 * Typ Revoke and grant access level dto.
 */
public class RevokeAndGrantAccessLevelDTO extends AccessLevelDto {

    @NotNull
    @Login
    private String login;

    /**
     * Tworzy nową instancję klasy Revoke and grant access level dto.
     */
    public RevokeAndGrantAccessLevelDTO() {
    }

    /**
     * Tworzy nową instancję klasy Revoke and grant access level dto.
     *
     * @param level level
     * @param login login
     */
    public RevokeAndGrantAccessLevelDTO(String level, String login) {
        super(level);
        this.login = login;
    }

    /**
     * Pobiera pole login.
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Ustawia pole login.
     *
     * @param login login
     */
    public void setLogin(String login) {
        this.login = login;
    }
}
