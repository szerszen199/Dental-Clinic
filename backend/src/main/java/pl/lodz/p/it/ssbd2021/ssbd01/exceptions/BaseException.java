package pl.lodz.p.it.ssbd2021.ssbd01.exceptions;

import javax.ejb.ApplicationException;

/**
 * The type Base exception.
 */
@ApplicationException(rollback = true)
public abstract class BaseException extends Exception {

    /**
     * Konstruktor - tworzy obiekt wyjątku.
     *
     * @param message treść wyjątku
     */
    protected BaseException(String message) {
        super(message);
    }

    /**
     * Konstruktor - tworzy obiekt wyjątku.
     *
     * @param message treść wyjątku
     * @param cause   powód wystąpienia wyjątku
     */
    protected BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
