package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto;

import java.time.LocalDateTime;
import java.util.Set;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Klasa dla AccountDto.
 */
public class AccountDto {

    @NotNull
    @Login
    private String login;

    @NotNull
    @Email
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

    @NotNull
    private Boolean active;

    @NotNull
    private String language;

    @NotNull
    private Long version;

    /**
     * Tworzy nową instancję klasy AccountDto.
     *
     * @param account klasa reprezentujacea konto użytkownika aplikacji
     */
    public AccountDto(Account account) {
        this.login = account.getLogin();
        this.email = account.getEmail();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.phoneNumber = account.getPhoneNumber();
        this.pesel = account.getPesel();
        this.active = account.getActive();
        this.language = account.getLanguage();
        this.version = account.getVersion();
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

    public String getFirstName() {
        return firstName;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
