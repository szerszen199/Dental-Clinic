package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;

import com.sun.istack.Nullable;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Klasa Dto dla nowo tworzonego konta.
 */
public class CreateAccountRequestDTO {

    @NotNull(message = I18n.LOGIN_NULL)
    @Login
    private String login;

    @NotNull(message = I18n.EMAIL_NULL)
    @Email(message = I18n.NOT_AN_EMAIL)
    @Size(min = 5, max = 100, message = I18n.EMAIL_INVALID_SIZE)
    private String email;

    @Size(min = 8, message = I18n.PASSWORD_INVALID_SIZE)
    @NotNull(message = I18n.PASSWORD_NULL)
    private String password;

    @NotNull(message = I18n.FIRST_NAME_NULL)
    @Size(min = 1, max = 50, message = I18n.FIRST_NAME_INVALID_SIZE)
    private String firstName;

    @NotNull(message = I18n.LAST_NAME_NULL)
    @Size(min = 1, max = 50, message = I18n.LAST_NAME_INVALID_SIZE)
    private String lastName;

    @Size(min = 9, max = 15, message = I18n.PHONE_NUMBER_INVALID_SIZE)
    private String phoneNumber;

    @Size(min = 11, max = 11, message = I18n.PESEL_INVALID_SIZE)
    private String pesel;

    @NotNull(message = I18n.LANGUAGE_NULL)
    @Pattern(regexp = "pl|en", message = I18n.LANGUAGE_NOT_IN_PATTERN)
    private String language;
    
    /**
     * Tworzy nową instancję klasy NewAccountDto.
     *
     * @param login       login konta
     * @param email       adres e-mail przypisany do konta
     * @param password    hasło konta
     * @param firstName   imię użytkownika
     * @param lastName    nazwisko użytkownika
     * @param phoneNumber numer telefonu
     * @param pesel       pesel
     * @param language    preferowany język użytkownika
     */
    public CreateAccountRequestDTO(String login, String email, String password, String firstName,
                                   String lastName, String phoneNumber, String pesel, String language) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
        this.language = language;
    }

    /**
     * Tworzy nową instancję klasy New account dto.
     */
    public CreateAccountRequestDTO() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
