package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto;

public class AccountDto {
    private String login;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String pesel;

    /**
     * Tworzy nową instancję klasy AccountDto.
     */
    public AccountDto() {
    }

    /**
     * Tworzy nową instancję klasy AccountDto.
     *
     * @param login         login konta
     * @param email         adres e-mail przypisany do konta
     * @param password      hasło konta
     * @param firstName     imię użytkownika
     * @param lastName      nazwisko użytkownika
     * @param phoneNumber   numer telefonu
     * @param pesel         pesel
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

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPesel() {
        return pesel;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
}
