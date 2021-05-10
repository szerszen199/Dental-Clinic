package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import javax.ejb.Local;

/**
 * Interfejs Access level manager.
 */
@Local
public interface AccessLevelManager {

    /**
     * Revoke access level - odebranie poziomu dostępu {@param level} kontowi o zadanym {@param id}.
     *
     * @param id    id uzytkownika, któremu zostanie odebrany poziom dostępu
     * @param level level odbierany poziom odstępu
     */
    void revokeAccessLevel(Long id, String level);

    /**
     * Revoke access level - odebranie poziomu dostępu {@param level} kontowi o zadanym {@param login}.
     *
     * @param login login uzytkownika, któremu zostanie odebrany poziom dostępu
     * @param level level odbierany poziom odstępu
     */
    void revokeAccessLevel(String login, String level);


    /**
     * Dodaje poziom dostępu {@param level} kontowi o loginie równym {@param login}.
     *
     * @param level nazwa poziomu dostępu konta
     * @param login login użytkownika, któremu zostanie dodany poziom dostępu
     */
    void addAccessLevel(String login, String level);

}
