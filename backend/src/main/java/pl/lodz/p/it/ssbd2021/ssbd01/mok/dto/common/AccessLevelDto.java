package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.common;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Typ Access level dto - dto dla poziomu dostępu.
 */
@NotNull
public abstract class AccessLevelDto {
    @NotNull(message = I18n.ACCESS_LEVEL_NULL)
    @Size(min = 7, max = 32, message = I18n.ACCESS_LEVEL_INVALID_SIZE)
    @Pattern(regexp = I18n.PATIENT + "|" + I18n.ADMIN + "|" + I18n.RECEPTIONIST + "|" + I18n.DOCTOR, message = I18n.ACCESS_LEVEL_INVALID_LEVEL)
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}


