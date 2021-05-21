package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LanguageDto {

    @NotNull
    @Pattern(regexp = "pl|PL|en|EN")
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LanguageDto() {
    }

    public LanguageDto(String language) {
        this.language = language;
    }
}
