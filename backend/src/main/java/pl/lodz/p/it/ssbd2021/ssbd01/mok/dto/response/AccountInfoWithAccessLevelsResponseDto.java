package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.common.AccessLevelDto;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa dla AccountInfoResponseDTO - dto odpowiedzi z loginem i poziomami dostępu.
 */
public class AccountInfoWithAccessLevelsResponseDto extends AccountInfoResponseDTO {

    @NotNull(message = I18n.LOGIN_NULL)
    @Login
    private String login;

    @NotNull
    private List<AccessLevelDto> accessLevelDtoList = new ArrayList<>();


    /**
     * Tworzy nową instancję klasy AccountDto.
     *
     * @param account klasa reprezentujacea konto użytkownika aplikacji
     */
    public AccountInfoWithAccessLevelsResponseDto(Account account) {
        super(account);
        this.login = account.getLogin();
        account.getAccessLevels().forEach(accessLevel ->
                accessLevelDtoList.add(new AccessLevelDtoWithActive(accessLevel.getLevel(), accessLevel.getActive())));
    }


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

    @Override
    @JsonbTransient
    public Map<String, String> getPayload() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("version", getVersion().toString());
        map.put("login", getLogin());
        return map;
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
         * Tworzy nową instancję klasy Access level dto with active.
         */
        public AccessLevelDtoWithActive() {
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
    }
}
