package pl.lodz.p.it.ssbd2021.ssbd01.mow.util;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs Repeatable.
 */
@FunctionalInterface
public interface Repeatable {
    /**
     * Metoda która ma zostać powtórzona.
     *
     * @throws AppBaseException w przypadku niepowodzenia
     */
    void repeat() throws AppBaseException;
}
