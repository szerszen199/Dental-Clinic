package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_ALREADY_CONFIRMED;
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
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.EMAIL_ALREADY_CHANGED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.EMAIL_CONFIRMATION_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.INVALID_CONFIRMATION_TOKEN;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.PASSWORD_ALREADY_CHANGED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.UPDATE_ACCOUNT_AFTER_SUCCESSFUL_LOGIN;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.UPDATE_ACCOUNT_AFTER_UNSUCCESSFUL_LOGIN;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.VERSION_MISMATCH;

/**
 * Typ Account exception - wyjątki dla konta.
 */
public class AppointmentException extends AppBaseException {

    /**
     * Tworzy nową instancję klasy wyjątku AppointmentException.
     *
     * @param message wiadomość zawarta w wyjątku
     */
    private AppointmentException(String message) {
        super(message);
    }

    /**
     * Tworzy nową instancję klasy wyjątku AppointmentException.
     *
     * @param message wiadomość zawarta w wyjątku
     * @param cause   przyczyna wystąpienia wyjątku
     */
    private AppointmentException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę utworzenia konta o loginie który już istnieje.
     *
     * @param cause przyczyna wystąpienia wyjątku
     * @return wyjątek typu AccountException
     */
    public static AppointmentException accountLoginExists(Throwable cause) {
        return new AppointmentException(ACCOUNT_LOGIN_ALREADY_EXISTS, cause);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę zablokowania konta.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException accountLockFailed() {
        return new AppointmentException(ACCOUNT_LOCKED_FAILED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę utworzenia konta o emailu który już istnieje.
     *
     * @param cause przyczyna wystąpienia wyjątku
     * @return wyjątek typu AccountException
     */
    public static AppointmentException accountEmailExists(Throwable cause) {
        return new AppointmentException(ACCOUNT_EMAIL_ALREADY_EXISTS, cause);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę utworzenia konta o loginie lub emailu lub peselu który już istnieje.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException accountLoginEmailPeselExists() {
        return new AppointmentException(ACCOUNT_LOGIN_EMAIL_PESEL_ALREADY_EXISTS);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę potwirdzenia konta emailem.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException emailConfirmationFailed() {
        return new AppointmentException(EMAIL_CONFIRMATION_FAILED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę ustawienia ciemnego motywu.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException accountSetDarkMode() {
        return new AppointmentException(ACCOUNT_SET_DARK_MODE_FAILED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę ustawienia języka.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException accountSetLanguage() {
        return new AppointmentException(ACCOUNT_SET_LANGUAGE_FAILED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę utworzenia konta, która się nie powiodła.
     *
     * @return wyjątek typu AppointmentException
     */
    public static AppointmentException appointmentCreationFailed() {
        return new AppointmentException(ACCOUNT_CREATION_FAILED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę wykonania operacji na zablokowanym koncie.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException accountIsBlocked() {
        return new AppointmentException(ACCOUNT_IS_BLOCKED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę wykonania operacji na nieistniejącym koncie.
     *
     * @param cause przyczyna wystąpienia wyjątku
     * @return wyjątek typu AccountException
     */
    public static AppointmentException noSuchAccount(Throwable cause) {
        return new AppointmentException(ACCOUNT_NOT_FOUND, cause);
    }

    public static AppointmentException getAllAccountsFailed() {
        return new AppointmentException(ACCOUNT_GET_ALL_ACCOUNTS_FAILED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę wykonania edycji konta.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException accountEditFailed() {
        return new AppointmentException(ACCOUNT_EDIT_FAILED);
    }


    /**
     * Tworzy wyjątek reprezentujący próbę potwierdzenia konta zakończoną niepowodzeniem.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException accountConfirmationByTokenFailed() {
        return new AppointmentException(ACCOUNT_CONFIRMATION_BY_TOKEN_FAILED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę aktywacji konta z użyciem niepoprawnego
     * tokenu aktywacyjnego.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException invalidConfirmationToken() {
        return new AppointmentException(INVALID_CONFIRMATION_TOKEN);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę aktywacji konta z użyciem niepoprawnego
     * tokenu aktywacyjnego.
     *
     * @param cause przyczyna wystąpienia wyjątku
     * @return wyjątek typu AccountException
     */
    public static AppointmentException invalidConfirmationToken(Throwable cause) {
        return new AppointmentException(INVALID_CONFIRMATION_TOKEN, cause);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę odblokowania konta.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException accountUnlockFailed() {
        return new AppointmentException(ACCOUNT_UNLOCKED_FAILED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę uaktualnienia po nieudanym logowaniu.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException updateAfterUnsuccessfulLogin() {
        return new AppointmentException(UPDATE_ACCOUNT_AFTER_UNSUCCESSFUL_LOGIN);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę uaktualnienia po udanym logowaniu.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException updateAfterSuccessfulLogin() {
        return new AppointmentException(UPDATE_ACCOUNT_AFTER_SUCCESSFUL_LOGIN);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę potwierdzenia konta po poprawnym potwierdzeniu.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException accountAlreadyConfirmed() {
        return new AppointmentException(ACCOUNT_ALREADY_CONFIRMED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę potwierdzenia zmiany emailu po zmianie emailu.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException emailAlreadyChanged() {
        return new AppointmentException(EMAIL_ALREADY_CHANGED);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę potwierdzenia zmiany emailu po zmianie emailu.
     *
     * @return wyjątek typu AccountException
     */
    public static AppointmentException passwordAlreadyChanged() {
        return new AppointmentException(PASSWORD_ALREADY_CHANGED);
    }

    /**
     * Tworzy wyjątek reprezentujący różne wartości wersji dla encji.
     * @return wyjątek typu AccountException
     */
    public static AppointmentException versionMismatchException() {
        return new AppointmentException(VERSION_MISMATCH);
    }
}
