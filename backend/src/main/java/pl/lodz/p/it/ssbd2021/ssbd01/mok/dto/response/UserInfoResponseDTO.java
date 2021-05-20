package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response;

/**
 * Dto dla odpowiedzi przy logowaniu z informacjami o użytkowniku.
 */
public class UserInfoResponseDTO {
    private String firstName;
    private String lastName;
    private boolean isDarkMode;
    private String language;

    /**
     * Tworzy nową instancję klasy.
     */
    public UserInfoResponseDTO() {
    }

    /**
     * Tworzy nową instancję klasy.
     *
     * @param firstName  first name
     * @param lastName   last name
     * @param isDarkMode is dark mode
     * @param language   language
     */
    public UserInfoResponseDTO(String firstName, String lastName, boolean isDarkMode, String language) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isDarkMode = isDarkMode;
        this.language = language;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isDarkMode() {
        return isDarkMode;
    }

    public String getLanguage() {
        return language;
    }
}
