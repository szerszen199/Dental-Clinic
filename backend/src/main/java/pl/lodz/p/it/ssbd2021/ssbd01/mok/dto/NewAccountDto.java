package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto;

import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Klasa Dto dla nowo tworzonego konta.
 */
public class NewAccountDto {

    @NotNull
    @Login
    private String login;

    @NotNull
    @Email
    @Size(min = 4, max = 100)
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;

    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;

    @Size(min = 9, max = 15)
    private String phoneNumber;


    @Size(min = 11, max = 11)
    private String pesel;

    @NotNull
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
    public NewAccountDto(String login, String email, String password, String firstName,
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
    public NewAccountDto() {
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
