package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Klasa definiująca główne operacje wykonywane na encjach typu Account.
 */
@Stateless(name = "AccountFacadeMok")
@PermitAll
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LogInterceptor.class)
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "ssbd01mokPU")
    private EntityManager em;

    /**
     * Tworzy nową instancję klasy AccountFacade.
     */
    public AccountFacade() {
        super(Account.class);
    }

    /**
     * Tworzy nową instancję klasy AccountFacade.
     *
     * @param entityClass entity class
     */
    public AccountFacade(Class<Account> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Wyszukuje konta na podstawie danego loginu.
     *
     * @param login login
     * @return konto
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public Account findByLogin(String login) throws AppBaseException {
        try {
            TypedQuery<Account> tq = em.createNamedQuery("Account.findByLogin", Account.class);
            tq.setParameter("login", login);
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw AccountException.noSuchAccount(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    /**
     * Wyszukuje konta na podstawie danego loginu lub emaila.
     *
     * @param login login
     * @param email email
     * @param pesel pesel
     * @return konto
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public Account findByLoginOrEmailOrPesel(String login, String email, String pesel) throws AppBaseException {
        try {
            TypedQuery<Account> tq = em.createNamedQuery("Account.findByLoginOrEmailOrPesel", Account.class);
            tq.setParameter("email", email);
            tq.setParameter("login", login);
            tq.setParameter("pesel", pesel);
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw AccountException.noSuchAccount(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    /**
     * Wyszukuje kont na podstawie pola 'enabled'.
     *
     * @param enabled wartość pola 'enabled'
     * @return lista kont o podanej wartości pola 'enabled'
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public List<Account> findByEnabled(Boolean enabled) throws AppBaseException {
        try {
            TypedQuery<Account> tq = em.createNamedQuery("Account.findByEnabled", Account.class);
            tq.setParameter("enabled", enabled);
            return tq.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        } catch (IllegalArgumentException e) {
            throw AppBaseException.mismatchedPersistenceArguments(e);
        }
    }

    /**
     * Wyszukuje kont na podstawie pola 'enabled'.
     *
     * @param active wartość pola 'enabled'
     * @return lista kont o podanej wartości pola 'enabled'
     * @throws AppBaseException wyjątek typu AppBaseException
     */
    public List<Account> findByActive(Boolean active) throws AppBaseException {
        try {
            TypedQuery<Account> tq = em.createNamedQuery("Account.findByActive", Account.class);
            tq.setParameter("active", active);
            return tq.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        } catch (IllegalArgumentException e) {
            throw AppBaseException.mismatchedPersistenceArguments(e);
        }
    }


    /**
     * Pobiera wszystkich pacjentów.
     *
     * @return Lista kont będących pacjentami
     * @throws AppBaseException w przypadku błędów
     */
    public List<Account> getAllPatients() throws AppBaseException {
        try {
            TypedQuery<Account> tq = em.createNamedQuery("Account.findByAccessLevel", Account.class);
            tq.setParameter("level", I18n.PATIENT);
            return tq.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        } catch (IllegalArgumentException e) {
            throw AppBaseException.mismatchedPersistenceArguments(e);
        }
    }
}
