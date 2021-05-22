package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.NotNull;

/**
 * Typ Edit another account request dto.
 */
public class EditAnotherAccountRequestDTO extends EditOwnAccountRequestDTO {

    @Login
    @NotNull
    private String login;

    /**
     * Ustawia pole login.
     *
     * @param login login
     */
    public void setLogin(String login) {
        this.login = login;
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
     * Tworzy nową instancję klasy Edit another account request dto.
     */
    public EditAnotherAccountRequestDTO() {
    }

    /**
     * Tworzy nową instancję klasy Edit another account request dto.
     *
     * @param login       login
     * @param email       email
     * @param firstName   first name
     * @param lastName    last name
     * @param phoneNumber phone number
     * @param pesel       pesel
     * @param version     version
     */
    public EditAnotherAccountRequestDTO(String login, String email, String firstName, String lastName, String phoneNumber, String pesel, Long version) {
        super(email, firstName, lastName, phoneNumber, pesel, version);
        this.login = login;
    }
}
