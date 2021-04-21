package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import javax.ejb.Local;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.BaseException;

/**
 * Interfejs Account manager.
 */
@Local
public interface AccountManager {
    /**
     * Confirm account.
     *
     * @param id id
     */
    void confirmAccount(Long id);

    /**
     * Confirm account.
     *
     * @param login login
     */
    void confirmAccount(String login);

    /**
     * Metoda służąca do blokowania konta.
     *
     * @param id identyfikator blokowanego konta
     * @throws BaseException bazowy wyjątek aplikacji
     */
    void lockAccount(Long id) throws BaseException;

    /**
     * Metoda służąca do odblokowywania konta.
     *
     * @param id identyfikator odblokowywanego konta
     * @throws BaseException bazowy wyjątek aplikacji
     */
    void unlockAccount(Long id) throws BaseException;

}