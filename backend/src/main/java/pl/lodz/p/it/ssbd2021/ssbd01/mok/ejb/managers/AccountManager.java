package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import javax.ejb.Local;

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
    public void confirmAccount(Long id);

    /**
     * Confirm account.
     *
     * @param login login
     */
    public void confirmAccount(String login);
}
