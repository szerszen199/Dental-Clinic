package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto;

import javax.validation.constraints.NotNull;

/**
 * Klasa DarkModeDto dla motywu ciemnego.
 */
public class DarkModeDto {

    @NotNull
    private boolean isDarkMode;

    public boolean isDarkMode() {
        return isDarkMode;
    }

    /**
     * Tworzy nową instancję klasy DarkModeDto.
     *
     * @param isDarkMode ustawienie trybu ciemnego
     */
    public DarkModeDto(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
    }

    /**
     * Tworzy nową instancję klasy DarkModeDto.
     */
    public DarkModeDto() {
    }
}
