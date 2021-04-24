package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AccessLevelMapper;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AccessLevelException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.BaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.DataValidationException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.HashGenerator;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Typ Account manager implementation.
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountManagerImplementation implements AccountManager {
    @Inject
    private AccountFacade accountFacade;

    @Inject
    private HashGenerator hashGenerator;

    @Override
    public void createAccount(Account account, AccessLevel accessLevel) {
        account.setPassword(hashGenerator.generateHash(account.getPassword()));
        accessLevel.setCreatedBy(account);
        accessLevel.setAccountId(account);
        account.getAccessLevels().add(accessLevel);
        account.setCreatedBy(account);
        accountFacade.create(account);
    }

    @Override
    public void confirmAccount(Long id) {
        accountFacade.find(id).setEnabled(true);
    }

    @Override
    public void confirmAccount(String login) {
        accountFacade.findByLogin(login).setEnabled(true);
    }

    @Override
    public void addAccessLevel(AccessLevel accessLevel, String login) throws AccessLevelException {
        Account account = accountFacade.findByLogin(login);
        accessLevel.setAccountId(account);
        accessLevel.setCreatedBy(account);
        account.getAccessLevels().add(accessLevel);
    }


    @Override
    public void lockAccount(Long id) throws BaseException {
        Account account = accountFacade.find(id);
        account.setActive(false);
        accountFacade.edit(account);
    }

    @Override
    public void unlockAccount(Long id) throws BaseException {
        Account account = accountFacade.find(id);
        account.setActive(true);
        accountFacade.edit(account);
    }

    @Override
    public void editAccount(Long id, Account account) throws BaseException {
        account.setModifiedBy(account);
        Account old = accountFacade.findByLogin(account.getLogin());
        if (old.getActive() != account.getActive() || old.getEnabled() != account.getEnabled() || !old.getPesel().equals(account.getPesel())) {
            throw new DataValidationException("Niepoprawna walidacja danych wejściowych");
        }
        accountFacade.edit(account);
    }
}
