package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Klasa Dto dla konta z poziomami dostępu.
 */
public class AccountAccessLevelDto {

    @NotNull
    @Size(min = 3, max = 3)
    private Set<AccessLevel> accessLevels;

    @NotNull
    @Login
    private String login;

    @NotNull
    private Long version;

    @NotNull
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    /**
     * Konstruktor klasy AccountAccessLevelDto.
     *
     * @param account konto z którego pobiermy dane
     */
    public AccountAccessLevelDto(Account account) {
        this.accessLevels = account.getAccessLevels();
        this.login = account.getLogin();
        this.version = account.getVersion();
    }

    public Set<AccessLevel> getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(Set<AccessLevel> accessLevels) {
        this.accessLevels = accessLevels;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
