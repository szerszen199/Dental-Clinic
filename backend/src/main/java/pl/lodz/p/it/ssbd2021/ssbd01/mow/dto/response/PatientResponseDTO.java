package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response;

import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Typ PatientResponseDTO - klasa DTO reprezentująca informacje o pacjentach.
 */
public class PatientResponseDTO {

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

    /**
     * Tworzy nową instancję klasy PatientResponseDTO.
     *
     * @param login login pacjenta
     * @param email email pacjenta
     * @param firstName imię pacjenta
     * @param lastName nazwisko pacjenta
     * @param phoneNumber numer telefonu pacjenta
     * @param pesel pesel pacjenta
     */
    public PatientResponseDTO(String login, String email, String firstName, String lastName, String phoneNumber, String pesel) {
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

    public void setEmail(String email) {
        this.email = email;
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

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
