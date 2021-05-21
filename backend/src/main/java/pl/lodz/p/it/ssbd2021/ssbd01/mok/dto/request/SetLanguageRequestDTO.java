package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Klasa LanguageDto dla języka.
 */
public class SetLanguageRequestDTO {

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
}
