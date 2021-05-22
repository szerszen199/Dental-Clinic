package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;

/**
 * Typ Confirm account request dto.
 */
public class ConfirmAccountRequestDTO {

    @NotNull(message = I18n.TOKEN_NULL)
    private String confirmToken;

    /**
     * Pobiera pole confirm token.
     *
     * @return confirm token
     */
    public String getConfirmToken() {
        return confirmToken;
    }

    /**
     * Ustawia pole confirm token.
     *
     * @param confirmToken confirm token
     */
    public void setConfirmToken(String confirmToken) {
        this.confirmToken = confirmToken;
    }

    /**
     * Tworzy nową instancję klasy Confirm account request dto.
     *
     * @param confirmToken confirm token
     */
    public ConfirmAccountRequestDTO(String confirmToken) {
        this.confirmToken = confirmToken;
    }
}
