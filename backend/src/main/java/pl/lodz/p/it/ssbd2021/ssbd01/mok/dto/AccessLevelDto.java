package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto;

public class AccessLevelDto {
    private String level;
    private String login;

    /**
     * Tworzy nową instancję klasy AccessLevelDto.
     */
    public AccessLevelDto() {
    }

    /**
     * Tworzy nową instancję klasy AccessLevelDto.
     *
     * @param level poziom dostępu
     * @param login login
     */
    public AccessLevelDto(String level, String login) {
        this.level = level;
        this.login = login;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
