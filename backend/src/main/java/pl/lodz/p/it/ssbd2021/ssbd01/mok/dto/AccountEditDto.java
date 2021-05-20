package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto;


import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Klasa Dto dla edytowanego konta.
 */
public class AccountEditDto {

    @NotNull
    @Login
    private String login;

    @NotNull
    @Email
    @Size(min = 4, max = 100)
    private String email;

    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 80)
    private String lastName;


    @Size(min = 9, max = 15)
    private String phoneNumber;


    @Size(min = 11, max = 11)
    private String pesel;

    /**
     * Tworzy nową instancję klasy Account edit dto.
     */
    public AccountEditDto() {
    }

    /**
     * Konstruktor klasy AccountEditDto.
     *
     * @param login       login
     * @param email       nowy email
     * @param firstName   nowe imię
     * @param lastName    nowe nazwisko
     * @param phoneNumber nowy numer telefonu
     * @param pesel       nowy pesel
     */
    public AccountEditDto(String login, String email, String firstName, String lastName, String phoneNumber, String pesel) {
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
