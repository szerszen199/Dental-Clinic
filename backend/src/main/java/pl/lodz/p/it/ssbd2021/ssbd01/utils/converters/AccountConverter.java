package pl.lodz.p.it.ssbd2021.ssbd01.utils.converters;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.AccountDto;

public class AccountConverter {

    /**
     * Tworzy nową instancję obiektu AccountEntity korzystając z danych z obiektu AccountDto (konwertuje obiekt).
     *
     * @param accountDto obiekt typu AccountDto
     * @return account
     */
    public static Account createAccountEntityFromDto(AccountDto accountDto) {
        return new Account(accountDto.getLogin(), accountDto.getEmail(), accountDto.getPassword(),
                accountDto.getFirstName(), accountDto.getLastName(), accountDto.getPhoneNumber(), accountDto.getPesel());
    }
}
