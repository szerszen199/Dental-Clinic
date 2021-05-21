package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Klasa LanguageDto dla języka.
 */
public class LanguageDto {

    @NotNull
    @Pattern(regexp = "pl|en")
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Tworzy nową instancję klasy LanguageDto.
     */
    public LanguageDto() {
    }

    /**
     * Tworzy nową instancję klasy LanguageDto.
     *
     * @param language język
     */
    public LanguageDto(String language) {
        this.language = language;
    }
}
