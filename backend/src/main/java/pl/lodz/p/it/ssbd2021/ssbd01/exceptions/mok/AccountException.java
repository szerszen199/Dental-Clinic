package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_EMAIL_ALREADY_EXISTS;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_IS_BLOCKED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_LOGIN_ALREADY_EXISTS;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_LOGIN_EMAIL_ALREADY_EXISTS;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_NOT_FOUND;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.INVALID_CONFIRMATION_TOKEN;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.MAIL_CONFIRMATION_PARSING_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_CREATION_FAILED;

/**
 * Typ Account exception.
 */
public class AccountException extends AppBaseException {

    /**
     * Tworzy nową instancję klasy wyjątku AccountException.
     *
     * @param message wiadomość zawarta w wyjątku
     */
    private AccountException(String message) {
        super(message);
    }

    /**
     * Tworzy nową instancję klasy wyjątku AccountException.
     *
     * @param message wiadomość zawarta w wyjątku
     * @param cause   przyczyna wystąpienia wyjątku
     */
    private AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę utworzenia konta o loginie który już istnieje.
     *
     * @param cause przyczyna wystąpienia wyjątku
     * @return wyjątek typu AccountException
     */
    public static AccountException accountLoginExists(Throwable cause) {
        return new AccountException(ACCOUNT_LOGIN_ALREADY_EXISTS, cause);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę utworzenia konta o emailu który już istnieje.
     *
     * @param cause przyczyna wystąpienia wyjątku
     * @return wyjątek typu AccountException
     */
    public static AccountException accountEmailExists(Throwable cause) {
        return new AccountException(ACCOUNT_EMAIL_ALREADY_EXISTS, cause);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę utworzenia konta o loginie lub emailu który już istnieje.
     *
     * @return wyjątek typu AccountException
     */
    public static AccountException accountLoginEmailExists() {
        return new AccountException(ACCOUNT_LOGIN_EMAIL_ALREADY_EXISTS);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę utworzenia konta, która się nie powiodła.
     *
     * @return wyjątek typu AccountException
     */
    public static AccountException accountCreationFailed() {
        return new AccountException(ACCOUNT_CREATION_FAILED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę wykonania operacji na zablokowanym koncie.
     *
     * @return wyjątek typu AccountException
     */
    public static AccountException accountIsBlocked() {
        return new AccountException(ACCOUNT_IS_BLOCKED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę wykonania operacji na nieistniejącym koncie.
     *
     * @param cause przyczyna wystąpienia wyjątku
     * @return wyjątek typu AccountException
     */
    public static AccountException noSuchAccount(Throwable cause) {
        return new AccountException(ACCOUNT_NOT_FOUND, cause);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę aktywacji konta z użyciem niepoprawnego
     * tokenu aktywacyjnego.
     *
     * @return wyjątek typu AccountException
     */
    public static AccountException invalidConfirmationToken() {
        return new AccountException(INVALID_CONFIRMATION_TOKEN);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę aktywacji konta z użyciem niepoprawnego
     * tokenu aktywacyjnego.
     *
     * @param cause przyczyna wystąpienia wyjątku
     * @return wyjątek typu AccountException
     */
    public static AccountException invalidConfirmationToken(Throwable cause) {
        return new AccountException(INVALID_CONFIRMATION_TOKEN, cause);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę aktywacji konta z użyciem niepoprawnego
     * tokenu aktywacyjnego.
     *
     * @param cause przyczyna wystąpienia wyjątku
     * @return wyjątek typu AccountException
     */
    public static AccountException mailConfirmationParsingError(Throwable cause) {
        return new AccountException(MAIL_CONFIRMATION_PARSING_ERROR, cause);
    }

}
