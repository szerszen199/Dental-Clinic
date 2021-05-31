package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Klasa LanguageDto dla języka.
 */
public class SetLanguageRequestDTO {

    @NotNull(message = I18n.LANGUAGE_NULL)
    @Pattern(regexp = "pl|en", message = I18n.LANGUAGE_NOT_IN_PATTERN)
    private String language;

    /**
     * Tworzy nową instancję klasy LanguageDto.
     */
    public SetLanguageRequestDTO() {
    }

    /**
     * Tworzy nową instancję klasy LanguageDto.
     *
     * @param language język
     */
    public SetLanguageRequestDTO(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
