package pl.lodz.p.it.ssbd2021.ssbd01.exceptions;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.ejb.ApplicationException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import java.io.IOException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.DATABASE_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.DATABASE_OPTIMISTIC_LOCK_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.LOAD_PROPERTIES_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.VERSION_MISMATCH;

/**
 * The type Base exception - bazowa apliakcyjna klasa wyjątku.
 */
@ApplicationException(rollback = true)
public class AppBaseException extends Exception {

    /**
     * Konstruktor - tworzy obiekt wyjątku.
     *
     * @param message treść wyjątku
     */
    protected AppBaseException(String message) {
        super(message);
    }

    /**
     * Konstruktor - tworzy obiekt wyjątku.
     *
     * @param message treść wyjątku
     * @param cause   powód wystąpienia wyjątku
     */
    protected AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Tworzy wyjątek reprezentujący wystąpienie konfliktu związanego z mechanizmem blokady optymistycznej.
     *
     * @param e wyjątek OptimisticLockException
     * @return wyjątek typu AppBaseException
     */
    public static AppBaseException optimisticLockError(OptimisticLockException e) {
        return new AppBaseException(DATABASE_OPTIMISTIC_LOCK_ERROR, e);
    }

    /**
     * Tworzy wyjątek reprezentujący wystąpienie konfliktu związanego z mechanizmem blokady optymistycznej.
     *
     * @return wyjątek typu AppBaseException
     */
    public static AppBaseException optimisticLockError() {
        return new AppBaseException(DATABASE_OPTIMISTIC_LOCK_ERROR);
    }

    /**
     * Tworzy wyjątek reprezentujący wystąpienie konfliktu związanego z mechanizmem ponawiania transakcji.
     *
     * @return wyjątek typu AppBaseException
     */
    public static AppBaseException transactionRepeatFailure() {
        return new AppBaseException(I18n.TRANSACTION_REPEAT_FAILED_ERROR);
    }


    /**
     * Tworzy wyjątek reprezentujący wystąpienie problemu z bazą danych.
     *
     * @param e wyjątek PersistenceException powodujący problem
     * @return wyjątek typu AppBaseException
     */
    public static AppBaseException databaseError(PersistenceException e) {
        return new AppBaseException(DATABASE_ERROR, e);
    }

    /**
     * Tworzy wyjątek reprezentujący wystąpienie problemu z ładowaniem pliku properties.
     *
     * @param e wyjątek IOException powodujący problem
     * @return wyjątek typu AppBaseException
     */
    public static AppBaseException propertiesError(IOException e) {
        return new AppBaseException(LOAD_PROPERTIES_ERROR, e);
    }

    /**
     * Tworzy wyjątek reprezentujący wystąpienie problemu z bazą danych.
     *
     * @param e wyjątek PersistenceException powodujący problem
     * @return wyjątek typu AppBaseException
     */
    public static AppBaseException mismatchedPersistenceArguments(IllegalArgumentException e) {
        return new AppBaseException(DATABASE_ERROR, e);
    }

    /**
     * Tworzy wyjątek reprezentujący różne wartości wersji dla encji.
     * @return wyjątek typu AppBaseException
     */
    public static AppBaseException versionMismatchException() {
        return new AppBaseException(VERSION_MISMATCH);
    }
}
