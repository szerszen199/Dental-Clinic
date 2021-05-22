package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;


import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Klasa NewPasswordDTO dla zmiany hasła.
 */
public class ChangePasswordRequestDTO {
    // TODO: 21.05.2021 Czy wersja ma być skoro porównujemy i tak czy hasło się zgadza poprzedniego? Narazie nei daję

    @Size(min = 8, message = I18n.PASSWORD_INVALID_SIZE)
    @NotNull(message = I18n.PASSWORD_NULL)
    private String oldPassword;

    @Size(min = 8, message = I18n.PASSWORD_INVALID_SIZE)
    @NotNull(message = I18n.PASSWORD_NULL)
    private String firstPassword;

    @Size(min = 8, message = I18n.PASSWORD_INVALID_SIZE)
    @NotNull(message = I18n.PASSWORD_NULL)
    private String secondPassword;

    /**
     * Tworzy nową instancję klasy NewPasswordDTO reprezentującej zmianę hasła własnego konta.
     *
     * @param oldPassword    aktualne hasło
     * @param firstPassword  pierwsze powtórzenie nowego hasła
     * @param secondPassword drugie powtórzenie nowego hasła
     */
    public ChangePasswordRequestDTO(String oldPassword, String firstPassword, String secondPassword) {
        this.oldPassword = oldPassword;
        this.firstPassword = firstPassword;
        this.secondPassword = secondPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
