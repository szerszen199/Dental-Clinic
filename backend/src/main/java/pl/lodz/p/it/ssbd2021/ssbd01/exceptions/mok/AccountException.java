package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_CONFIRMATION_BY_TOKEN_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_CREATION_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_EDIT_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_EMAIL_ALREADY_EXISTS;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_GET_ALL_ACCOUNTS_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_IS_BLOCKED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_LOCKED_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_LOGIN_ALREADY_EXISTS;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_LOGIN_EMAIL_PESEL_ALREADY_EXISTS;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_NOT_FOUND;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_SET_DARK_MODE_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_SET_LANGUAGE_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_UNLOCKED_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.EMAIL_CONFIRMATION_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.INVALID_CONFIRMATION_TOKEN;

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

    public static AccountException accountLockFailed() {
        return new AccountException(ACCOUNT_LOCKED_FAILED);
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
    public static AccountException accountLoginEmailPeselExists() {
        return new AccountException(ACCOUNT_LOGIN_EMAIL_PESEL_ALREADY_EXISTS);
    }

    public static AccountException emailConfirmationFailed() {
        return new AccountException(EMAIL_CONFIRMATION_FAILED);
    }

    public static AccountException accountSetDarkMode() {
        return new AccountException(ACCOUNT_SET_DARK_MODE_FAILED);
    }

    public static AccountException accountSetLanguage() {
        return new AccountException(ACCOUNT_SET_LANGUAGE_FAILED);
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

    public static AccountException getAllAccountsFailed() {
        return new AccountException(ACCOUNT_GET_ALL_ACCOUNTS_FAILED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę wykonania edycji konta.
     *
     * @return wyjątek typu AccountException
     */
    public static AccountException accountEditFailed() {
        return new AccountException(ACCOUNT_EDIT_FAILED);
    }


    /**
     * Tworzy wyjątek reprezentujący próbę potwierdzenia konta zakończoną niepowodzeniem.
     *
     * @return wyjątek typu AccountException
     */
    public static AccountException accountConfirmationByTokenFailed() {
        return new AccountException(ACCOUNT_CONFIRMATION_BY_TOKEN_FAILED);
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

    public static AccountException accountUnlockFailed() {
        return new AccountException(ACCOUNT_UNLOCKED_FAILED);
    }
}
