package pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_EDIT_VALIDATION_ERROR;

/**
 * Wyjątek błędnej walidacji danych.
 */
public class DataValidationException extends AppBaseException {

    /**
     * Tworzy nową instancję klasy wyjątku DataValidationException.
     *
     * @param message wiadomość zawarta w wyjątku
     * @param cause   przyczyna wystąpienia wyjątku
     */
    public DataValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Tworzy nową instancję klasy wyjątku DataValidationException.
     *
     * @param message wiadomość zawarta w wyjątku
     */
    public DataValidationException(String message) {
        super(message);
    }

    /**
     * Wyjątek reprezentujący błędną walidację danych konta podczas edycji.
     *
     * @return wyjątek typu DataValidationExceptionApp
     */
    public static DataValidationException accountEditValidationError() {
        return new DataValidationException(ACCOUNT_EDIT_VALIDATION_ERROR);
    }

}
