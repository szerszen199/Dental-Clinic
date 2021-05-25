package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;

/**
 * Klasa SetDarkModeRequestDTO dla motywu ciemnego.
 */
public class SetDarkModeRequestDTO {

    @NotNull(message = I18n.DARK_MODE_NULL)
    private boolean darkMode;

    public boolean getDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    /**
     * Tworzy nową instancję klasy DarkModeDto.
     *
     * @param darkMode ustawienie trybu ciemnego
     */
    public SetDarkModeRequestDTO(boolean darkMode) {
        this.darkMode = darkMode;
    }

    /**
     * Tworzy nową instancję klasy DarkModeDto.
     */
    public SetDarkModeRequestDTO() {
    }
}
