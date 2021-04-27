package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto;


/**
 * Typ NewPasswordDTO.
 */
public class NewPasswordDTO {
    
    private String oldPassword;
    private String firstPassword;
    private String secondPassword;

    /**
     * Tworzy nową instancję klasy NewPasswordDTO reprezentującej zmianę hasła własnego konta.
     *
     * @param oldPassword    aktualne hasło
     * @param firstPassword  pierwsze powtórzenie nowego hasła
     * @param secondPassword drugie powtórzenie nowego hasła
     */
    public NewPasswordDTO(String oldPassword, String firstPassword, String secondPassword) {
        this.oldPassword = oldPassword;
        this.firstPassword = firstPassword;
        this.secondPassword = secondPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getFirstPassword() {
        return firstPassword;
    }

    public void setFirstPassword(String firstPassword) {
        this.firstPassword = firstPassword;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }
}
