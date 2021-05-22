package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.common;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public abstract class AccessLevelDto {
    @NotNull
    @Size(min = 7, max = 32)
    @Pattern(regexp = I18n.PATIENT + "|" + I18n.ADMIN + "|" + I18n.RECEPTIONIST + "|" + I18n.DOCTOR)
    private String level;

    /**
     * Tworzy nową instancję klasy AccessLevelDto.
     */
    public AccessLevelDto() {
    }

    /**
     * Tworzy nową instancję klasy AccessLevelDto.
     *
     * @param level poziom dostępu
     */
    public AccessLevelDto(String level) {
        this.level = level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}


