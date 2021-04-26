package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto;

import java.time.LocalDateTime;
import java.util.Set;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;

/**
 * Klasa dla AccountDto.
 */
public class AccountDto {

    private Long id;
    private String login;
    private String email;
    private Set<AccessLevel> accessLevels;
    private String firstName;
    private String password;
    private String lastName;
    private String phoneNumber;
    private String pesel;
    private Boolean active;
    private Boolean enabled;
    private LocalDateTime lastSuccessfulLogin;
    private String lastSuccessfulLoginIp;
    private LocalDateTime lastUnsuccessfulLogin;
    private String lastUnsuccessfulLoginIp;
    private Integer unsuccessfulLoginCounter;
    private String language;

    /**
     * Tworzy nową instancję klasy AccountDto.
     *
     * @param login       login konta
     * @param email       adres e-mail przypisany do konta
     * @param password    hasło konta
     * @param firstName   imię użytkownika
     * @param lastName    nazwisko użytkownika
     * @param phoneNumber numer telefonu
     * @param pesel       pesel
     */
    public AccountDto(String login, String email, String password, String firstName, String lastName, String phoneNumber, String pesel) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
    }

    /**
     * Tworzy nową instancję klasy AccountDto.
     *
     * @param account klasa reprezentujacea konto użytkownika aplikacji
     */
    public AccountDto(Account account) {
        this.id = account.getId();
        this.login = account.getLogin();
        this.email = account.getEmail();
        this.accessLevels = account.getAccessLevels();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.phoneNumber = account.getPhoneNumber();
        this.pesel = account.getPesel();
        this.active = account.getActive();
        this.enabled = account.getEnabled();
        this.lastSuccessfulLogin = account.getLastSuccessfulLogin();
        this.lastSuccessfulLoginIp = account.getLastSuccessfulLoginIp();
        this.lastUnsuccessfulLogin = account.getLastUnsuccessfulLogin();
        this.lastUnsuccessfulLoginIp = account.getLastUnsuccessfulLoginIp();
        this.unsuccessfulLoginCounter = account.getUnsuccessfulLoginCounter();
        this.language = account.getLanguage();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<AccessLevel> getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(Set<AccessLevel> accessLevels) {
        this.accessLevels = accessLevels;
    }

    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getLastSuccessfulLogin() {
        return lastSuccessfulLogin;
    }

    public void setLastSuccessfulLogin(LocalDateTime lastSuccessfulLogin) {
        this.lastSuccessfulLogin = lastSuccessfulLogin;
    }

    public String getLastSuccessfulLoginIp() {
        return lastSuccessfulLoginIp;
    }

    public void setLastSuccessfulLoginIp(String lastSuccessfulLoginIp) {
        this.lastSuccessfulLoginIp = lastSuccessfulLoginIp;
    }

    public LocalDateTime getLastUnsuccessfulLogin() {
        return lastUnsuccessfulLogin;
    }

    public void setLastUnsuccessfulLogin(LocalDateTime lastUnsuccessfulLogin) {
        this.lastUnsuccessfulLogin = lastUnsuccessfulLogin;
    }

    public String getLastUnsuccessfulLoginIp() {
        return lastUnsuccessfulLoginIp;
    }

    public void setLastUnsuccessfulLoginIp(String lastUnsuccessfulLoginIp) {
        this.lastUnsuccessfulLoginIp = lastUnsuccessfulLoginIp;
    }

    public Integer getUnsuccessfulLoginCounter() {
        return unsuccessfulLoginCounter;
    }

    public void setUnsuccessfulLoginCounter(Integer unsuccessfulLoginCounter) {
        this.unsuccessfulLoginCounter = unsuccessfulLoginCounter;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
