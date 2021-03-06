package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Typ Login request dto - dto przy tworzeniu zapytania logowania.
 */
public class AuthenticationRequestDTO {
    @NotNull(message = I18n.LOGIN_NULL)
    @Login
    private String username;

    @Size(min = 8, message = I18n.PASSWORD_INVALID_SIZE)
    @NotNull(message = I18n.PASSWORD_NULL)
    private String password;

    /**
     * Tworzy nową instancję klasy Login request dto.
     */
    public AuthenticationRequestDTO() {
    }

    /**
     * Tworzy nową instancję klasy Login request dto.
     *
     * @param username username
     * @param password password
     */
    public AuthenticationRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Pobiera pole username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Ustawia pole username.
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Pobiera pole password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Ustawia pole password.
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Zamienia login i hasło z dto na Credential potrzebne do uwierzytelniania.
     *
     * @return UsernamePasswordCredential z loginu i hasła.
     */
    public UsernamePasswordCredential toCredential() {
        return new UsernamePasswordCredential(username, password);
    }
}
