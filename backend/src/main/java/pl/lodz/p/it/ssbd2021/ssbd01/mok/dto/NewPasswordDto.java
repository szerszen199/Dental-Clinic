package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto;


import javax.validation.constraints.Size;

/**
 * Klasa NewPasswordDTO dla zmiany hasła.
 */
public class NewPasswordDto {
    
    @Size(min = 8)
    private String oldPassword;
    
    @Size(min = 8)
    private String firstPassword;
    
    @Size(min = 8)
    private String secondPassword;

    /**
     * Tworzy nową instancję klasy NewPasswordDTO reprezentującej zmianę hasła własnego konta.
     *
     * @param oldPassword    aktualne hasło
     * @param firstPassword  pierwsze powtórzenie nowego hasła
     * @param secondPassword drugie powtórzenie nowego hasła
     */
    public NewPasswordDto(String oldPassword, String firstPassword, String secondPassword) {
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
