package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;


import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Klasa Dto dla edytowanego konta.
 */
// TODO: 21.05.2021 Poprawić zapytanie na froncie
public class EditOwnAccountRequestDTO {

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

    @NotNull
    private Long version;

    /**
     * Konstruktor klasy AccountEditDto.
     */
    public EditOwnAccountRequestDTO() {
    }

    /**
     * Konstruktor klasy AccountEditDto.
     *
     * @param email       nowy email
     * @param firstName   nowe imię
     * @param lastName    nowe nazwisko
     * @param phoneNumber nowy numer telefonu
     * @param pesel       nowy pesel
     * @param version     version
     */
    public EditOwnAccountRequestDTO(String email, String firstName, String lastName, String phoneNumber, String pesel, Long version) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
        this.version = version;
    }

    /**
     * Pobiera pole version.
     *
     * @return version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Ustawia pole version.
     *
     * @param version version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * Pobiera pole email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ustawia pole email.
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Pobiera pole first name.
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Ustawia pole first name.
     *
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Pobiera pole last name.
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Ustawia pole last name.
     *
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Pobiera pole phone number.
     *
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Ustawia pole phone number.
     *
     * @param phoneNumber phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Pobiera pole pesel.
     *
     * @return pesel
     */
    public String getPesel() {
        return pesel;
    }

    /**
     * Ustawia pole pesel.
     *
     * @param pesel pesel
     */
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

}
