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

    /**
     * Revoke access level - odebranie poziomu dostępu {@param level} kontowi o zadanym {@param id}.
     *
     * @param id    id
     * @param level level
     */
    public void revokeAccessLevel(Long id, String level);

    /**
     * Revoke access level - odebranie poziomu dostępu {@param level} kontowi o zadanym {@param login}.
     *
     * @param login login
     * @param level level
     */
    public void revokeAccessLevel(String login, String level);

}
