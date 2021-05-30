package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;


import org.hibernate.validator.constraints.pl.PESEL;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignableEntity;

import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa Dto dla edytowanego konta.
 */
public class EditOwnAccountRequestDTO implements SignableEntity {


    // Uważam, że przy edycji onta wszystkie pola powinny być nullable i zmieniane tylko odpowiednie pola

    @Email(message = I18n.NOT_AN_EMAIL)
    @Size(min = 4, max = 100, message = I18n.EMAIL_INVALID_SIZE)
    private String email;

    @Size(min = 1, max = 50, message = I18n.FIRST_NAME_INVALID_SIZE)
    private String firstName;

    @Size(min = 1, max = 80, message = I18n.LAST_NAME_INVALID_SIZE)
    private String lastName;

    @Size(min = 9, max = 15, message = I18n.PHONE_NUMBER_INVALID_SIZE)
    private String phoneNumber;

    @Size(min = 11, max = 11, message = I18n.PESEL_INVALID_SIZE)
    @PESEL
    private String pesel;

    @NotNull(message = I18n.VERSION_NULL)
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
     * @param version     version
     * @param pesel       pesel
     */
    public EditOwnAccountRequestDTO(String email, String firstName, String lastName, String phoneNumber, Long version, String pesel) {
        this.pesel = pesel;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.version = version;
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


    @Override
    @JsonbTransient
    public Map<String, String> getPayload() {
        Map<String,String> map = new HashMap<>();
        map.put("version", String.valueOf(getVersion()));
        return map;
    }
}
