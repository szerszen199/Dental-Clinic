package pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers;

import pl.lodz.p.it.ssbd2021.ssbd01.common.Levels;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.DataValidationException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordsNotMatchException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.PasswordsSameException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.HashGenerator;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.RandomPasswordGenerator;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.core.Context;
import java.util.List;
import java.util.stream.Stream;


/**
 * Typ Account manager implementation.
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountManagerImplementation implements AccountManager {
    @Inject
    private AccountFacade accountFacade;

    @Context
    private SecurityContext securityContext;

    @Inject
    private HashGenerator hashGenerator;

    @Inject
    private RandomPasswordGenerator passwordGenerator;

    @Override
    public void createAccount(Account account, AccessLevel accessLevel) throws AppBaseException {
        account.setPassword(hashGenerator.generateHash(account.getPassword()));
        accessLevel.setCreatedBy(account);
        accessLevel.setAccountId(account);
        account.getAccessLevels().add(accessLevel);
        account.setCreatedBy(account);
        accountFacade.create(account);
    }

    @Override
    public void confirmAccount(Long id) throws AppBaseException {
        accountFacade.find(id).setEnabled(true);
    }

    @Override
    public void confirmAccount(String login) throws AppBaseException {
        accountFacade.findByLogin(login).setEnabled(true);
    }

    @Override
    public Account getLoggedInAccount() throws AppBaseException {
        if (securityContext.getCallerPrincipal() == null) {
            return null;
        } else {
            return accountFacade.findByLogin(securityContext.getCallerPrincipal().getName());
        }
    }

    @Override
    public void addAccessLevel(AccessLevel accessLevel, String login) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        accessLevel.setAccountId(account);
        accessLevel.setCreatedBy(account);
        account.getAccessLevels().add(accessLevel);
    }


    @Override
    public void lockAccount(Long id) throws AppBaseException {
        Account account = accountFacade.find(id);
        account.setActive(false);
        accountFacade.edit(account);
    }

    @Override
    public void unlockAccount(Long id) throws AppBaseException {
        Account account = accountFacade.find(id);
        account.setActive(true);
        accountFacade.edit(account);
    }

    @Override
    public void editAccount(Account account) throws AppBaseException {
        account.setModifiedBy(account);
        Account old = accountFacade.findByLogin(account.getLogin());
        if (old.getActive() != account.getActive() || old.getEnabled() != account.getEnabled() || !old.getPesel().equals(account.getPesel())) {
            throw DataValidationException.accountEditValidationError();
        }
        accountFacade.edit(account);
    }

    @Override
    public void editOtherAccount(Account account) throws AppBaseException {
        account.setModifiedBy(getLoggedInAccount());
        Account old = accountFacade.findByLogin(account.getLogin());
        if (old.getActive() != account.getActive() || old.getEnabled() != account.getEnabled() || !old.getPesel().equals(account.getPesel())) {
            throw DataValidationException.accountEditValidationError();
        }
        accountFacade.edit(account);
    }

    @Override
    public List<Account> getAllAccounts() throws AppBaseException {
        return accountFacade.findAll();
    }

    @Override
    public void changePassword(Account account, String oldPassword, String newPassword) throws AppBaseException {
        this.verifyOldPassword(account.getPassword(), oldPassword);
        this.validateNewPassword(account.getPassword(), newPassword);
        account.setPassword(hashGenerator.generateHash(newPassword));
        accountFacade.edit(account);
    }

    @Override
    public Account findByLogin(String login) throws AppBaseException {
        return accountFacade.findByLogin(login);
    }

    @Override
    public void resetPassword(Long id) throws AppBaseException {
        Account account = accountFacade.find(id);
        String generatedPassword = passwordGenerator.generate(8);
        String newPasswordHash = hashGenerator.generateHash(generatedPassword);

        account.setPassword(newPasswordHash);
        account.setPassword(generateNewRandomPassword());
        // TODO: send mail with new password
    }

    @Override
    public void resetPassword(Account account) {
        account.setPassword(generateNewRandomPassword());
        // TODO: send mail with new password
    }

    private String generateNewRandomPassword() {
        String generatedPassword = passwordGenerator.generate(8);
        return hashGenerator.generateHash(generatedPassword);
    }

    @Override
    public void setDarkMode(Account account, boolean isDarkMode) throws AppBaseException {
        account.setDarkMode(isDarkMode);
        accountFacade.edit(account);
    }

    private void verifyOldPassword(String currentPasswordHash, String oldPassword) throws AppBaseException {
        if (!currentPasswordHash.contentEquals(hashGenerator.generateHash(oldPassword))) {
            throw PasswordsNotMatchException.currentPasswordNotMatch();
        }
    }

    private void validateNewPassword(String currentPasswordHash, String newPassword) throws AppBaseException {
        if (currentPasswordHash.contentEquals(hashGenerator.generateHash(newPassword))) {
            throw PasswordsSameException.passwordsNotDifferent();
        }
    }

    @Override
    public boolean isAdmin(Account account) {
        Stream<AccessLevel> accessLevels = account.getAccessLevels().stream();
        return accessLevels.anyMatch(level -> (
                level.getLevel().equals(Levels.ADMINISTRATOR.getLevel())
                        && level.getActive()
        ));
    }
}
