package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.common.AccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Klasa dla AccountDto.
 */
public class AccountInfoResponseDTO {

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

    @NotNull
    private List<AccessLevelDto> accessLevelDtoList = new ArrayList<>();

    /**
     * Pobiera pole access level dto list.
     *
     * @return access level dto list
     */
    public List<AccessLevelDto> getAccessLevelDtoList() {
        return accessLevelDtoList;
    }

    /**
     * Ustawia pole access level dto list.
     *
     * @param accessLevelDtoList access level dto list
     */
    public void setAccessLevelDtoList(List<AccessLevelDto> accessLevelDtoList) {
        this.accessLevelDtoList = accessLevelDtoList;
    }

    /**
     * Tworzy nową instancję klasy AccountDto.
     *
     * @param account klasa reprezentujacea konto użytkownika aplikacji
     */
    public AccountInfoResponseDTO(Account account) {
        this.login = account.getLogin();
        this.email = account.getEmail();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.phoneNumber = account.getPhoneNumber();
        this.pesel = account.getPesel();
        this.active = account.getActive();
        this.language = account.getLanguage();
        this.version = account.getVersion();
        // TODO: 21.05.2021 Rozwiązać problem lazy loadingu (aktualnie ustawiłem fetch na eager)
        account.getAccessLevels().forEach(
                accessLevel -> accessLevelDtoList.add(new AccessLevelDtoWithActive(accessLevel.getLevel(), accessLevel.getActive())));
    }

    /**
     * Pobiera pole login.
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Ustawia pole login.
     *
     * @param login login
     */
    public void setLogin(String login) {
        this.login = login;
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
     * Ustawia pole first name.
     *
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
     * Pobiera pole active.
     *
     * @return active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Ustawia pole active.
     *
     * @param active active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Pobiera pole language.
     *
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Ustawia pole language.
     *
     * @param language language
     */
    public void setLanguage(String language) {
        this.language = language;
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
     * Typ Access level dto with active.
     */
    public static class AccessLevelDtoWithActive extends AccessLevelDto {

        @NotNull
        private Boolean active;

        /**
         * Tworzy nową instancję klasy Access level dto with active.
         *
         * @param level  level
         * @param active active
         */
        public AccessLevelDtoWithActive(String level, Boolean active) {
            super(level);
            this.active = active;
        }

        /**
         * Pobiera pole active.
         *
         * @return active
         */
        public Boolean getActive() {
            return active;
        }

        /**
         * Ustawia pole active.
         *
         * @param active active
         */
        public void setActive(Boolean active) {
            this.active = active;
        }

        /**
         * Tworzy nową instancję klasy Access level dto with active.
         */
        public AccessLevelDtoWithActive() {
        }
    }
}
