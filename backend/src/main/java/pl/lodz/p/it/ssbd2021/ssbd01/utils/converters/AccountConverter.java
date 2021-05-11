package pl.lodz.p.it.ssbd2021.ssbd01.utils.converters;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.AccountEditDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.NewAccountDto;

public class AccountConverter {
    //FIXME account dto cant contain password

    /**
     * Tworzy nową instancję obiektu AccountEntity korzystając z danych z obiektu AccountEditDto (konwertuje obiekt).
     *
     * @param accountDto obiekt typu AccountDto
     * @param account    konto które dla którego edytowanie są dane
     * @return account
     */
    public static Account createAccountEntityFromDto(AccountEditDto accountDto, Account account) {
        return new Account(account.getLogin(), accountDto.getEmail(), account.getPassword(),
                accountDto.getFirstName(), accountDto.getLastName(), accountDto.getPhoneNumber(), accountDto.getPesel());
    }

    /**
     * Tworzy nową instancję obiektu AccountEntity korzystając z danych z obiektu NewAccountDto (konwertuje obiekt).
     *
     * @param newAccountDto obiekt typu NewAccountDto
     * @return account
     */
    public static Account createAccountEntityFromDto(NewAccountDto newAccountDto) {
        return new Account(newAccountDto.getLogin(), newAccountDto.getEmail(), newAccountDto.getPassword(),
                newAccountDto.getFirstName(), newAccountDto.getLastName(), newAccountDto.getPhoneNumber(),
                newAccountDto.getPesel());
    }
}
