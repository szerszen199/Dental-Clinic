package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.common;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Klasa Dto dla nowo tworzonego konta.
 */
public class SetNewPasswordDto {

    @NotNull
    @Login
    private String login;

    @Size(min = 8)
    @NotNull(message = I18n.PASSWORD_NULL)
    private String newPassword;

    /**
     * Tworzy nową instancję klasy SetNewPasswordDto.
     *
     * @param login         login konta
     * @param newPassword   nowe hasło
     */
    public SetNewPasswordDto(String login, String newPassword) {
        this.login = login;
        this.newPassword = newPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
