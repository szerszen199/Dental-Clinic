package pl.lodz.p.it.ssbd2021.ssbd01.utils.converters;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.NewAccountByAdminDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.CreateAccountRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.request.EditOwnAccountRequestDTO;

/**
 * Klasa konwertera konta.
 */
public class AccountConverter {

    /**
     * Tworzy nową instancję obiektu AccountEntity korzystając z danych z obiektu AccountEditDto (konwertuje obiekt).
     *
     * @param accountDto obiekt typu AccountDto
     * @param account    konto które dla którego edytowanie są dane
     * @return account
     */
    public static Account createAccountEntityFromDto(EditOwnAccountRequestDTO accountDto, Account account) {
        return new Account(account.getLogin(), accountDto.getEmail(), account.getPassword(),
                accountDto.getFirstName(), accountDto.getLastName(), accountDto.getPhoneNumber(), accountDto.getPesel(), account.getLanguage());
    }

    /**
     * Tworzy nową instancję obiektu AccountEntity korzystając z danych z obiektu NewAccountDto (konwertuje obiekt).
     *
     * @param createAccountRequestDTO obiekt typu NewAccountDto
     * @return account
     */
    public static Account createAccountEntityFromDto(CreateAccountRequestDTO createAccountRequestDTO) {
        return new Account(createAccountRequestDTO.getLogin(), createAccountRequestDTO.getEmail(), createAccountRequestDTO.getPassword(),
                createAccountRequestDTO.getFirstName(), createAccountRequestDTO.getLastName(), createAccountRequestDTO.getPhoneNumber(),
                createAccountRequestDTO.getPesel(), createAccountRequestDTO.getLanguage());
    }

    /**
     * Tworzy nową instancję obiektu AccountEntity korzystając z danych z obiektu NewAccountByAdminDto (konwertuje obiekt).
     *
     * @param newAccountByAdminDto obiekt typu NewAccountByAdminDto
     * @return the account
     */
    public static Account createAccountByAdminEntityFromDto(NewAccountByAdminDto newAccountByAdminDto) {
        return new Account(newAccountByAdminDto.getLogin(), newAccountByAdminDto.getEmail(),
                newAccountByAdminDto.getFirstName(), newAccountByAdminDto.getLastName(),
                newAccountByAdminDto.getPhoneNumber(), newAccountByAdminDto.getPesel(), newAccountByAdminDto.getLanguage());
    }

}
