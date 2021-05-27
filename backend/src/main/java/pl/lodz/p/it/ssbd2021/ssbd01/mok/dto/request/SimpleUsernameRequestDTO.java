package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.NotNull;

/**
 * Typ Simple username request dto.
 */
public class SimpleUsernameRequestDTO {

    @NotNull(message = I18n.LOGIN_NULL)
    @Login
    private String login;

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

    /**
     * Tworzy nową instancję klasy Simple username request dto.
     */
    public SimpleUsernameRequestDTO() {
    }

    /**
     * Tworzy nową instancję klasy Simple username request dto.
     *
     * @param login login
     */
    public SimpleUsernameRequestDTO(String login) {
        this.login = login;
    }
}
