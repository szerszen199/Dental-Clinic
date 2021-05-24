package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccessLevelException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;

import javax.ejb.Local;

/**
 * Interfejs Access level manager.
 */
@Local
public interface AccessLevelManager {

    /**
     * Revoke access level - odebranie poziomu dostępu {@param level} kontowi o zadanym {@param login}.
     *
     * @param login login uzytkownika, któremu zostanie odebrany poziom dostępu
     * @param level level odbierany poziom odstępu
     * @throws AccessLevelException wyjątek typu AccessLevelException
     * @throws AccountException     wyjątek typu AccountException
     */
    void revokeAccessLevel(String login, String level) throws AccessLevelException, AccountException;

    /**
     * Dodaje poziom dostępu {@param level} kontowi o loginie równym {@param login}.
     *
     * @param level nazwa poziomu dostępu konta
     * @param login login użytkownika, któremu zostanie dodany poziom dostępu
     * @throws AccessLevelException wyjątek typu AccessLevelException
     * @throws AccountException     wyjątek typu AccountException
     */
    void addAccessLevel(String login, String level) throws AccessLevelException, AccountException;

    /**
     * Sprawdza czy ostatnia transakcja się powiodła.
     *
     * @return true jeśli ostatnia transakcja się nie powiodła, false w przeciwnym wypadku.
     */
    boolean isLastTransactionRollback();
}
