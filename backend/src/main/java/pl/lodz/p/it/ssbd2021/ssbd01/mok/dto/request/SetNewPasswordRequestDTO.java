package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;


import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Klasa SetNewPasswordRequestDTO dla ustawienia nowego hasła po resetowaniu.
 */
public class SetNewPasswordRequestDTO {

    @NotNull(message = I18n.TOKEN_NULL)
    private String confirmToken;

    @Size(min = 8, message = I18n.PASSWORD_INVALID_SIZE)
    @NotNull(message = I18n.PASSWORD_NULL)
    private String firstPassword;

    @Size(min = 8, message = I18n.PASSWORD_INVALID_SIZE)
    @NotNull(message = I18n.PASSWORD_NULL)
    private String secondPassword;


    /**
     * Tworzy nową instancję klasy ChangePasswordRequestDTO.
     */
    public SetNewPasswordRequestDTO() {
    }

    /**
     * Tworzy nową instancję klasy ChangePasswordRequestDTO reprezentującej zmianę hasła własnego konta.
     *
     * @param confirmToken   confirm token
     * @param firstPassword  pierwsze powtórzenie nowego hasła
     * @param secondPassword drugie powtórzenie nowego hasła
     */
    public SetNewPasswordRequestDTO(String confirmToken, String firstPassword, String secondPassword) {
        this.confirmToken = confirmToken;
        this.firstPassword = firstPassword;
        this.secondPassword = secondPassword;
    }

    public void setConfirmToken(String confirmToken) {
        this.confirmToken = confirmToken;
    }

    public String getConfirmToken() {
        return confirmToken;
    }

    public String getFirstPassword() {
        return firstPassword;
    }

    public void setFirstPassword(String firstPassword) {
        this.firstPassword = firstPassword;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }
}
