package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;

import java.util.ArrayList;
import java.util.List;

public class GetAllPatientsResponseDTO {

    List<PatientDTO> patients;

    /**
     * Tworzy nową instancję klasy GetAllPatientsResponseDTO.
     *
     * @param accounts lista kont
     */
    public GetAllPatientsResponseDTO(List<Account> accounts) {
        this.patients = new ArrayList<>();
        for (Account account : accounts) {
            patients.add(new PatientDTO((account)));
        }
    }

    public List<PatientDTO> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientDTO> patients) {
        this.patients = patients;
    }

    public static class PatientDTO {
        private final String login;
        private final Long id;
        private final String name;
        private final String lastName;
        private final String email;
        private final String phoneNumber;
        private final String pesel;

        /**
         * Tworzy nową instancję klasy PatientDTO.
         *
         * @param account the account
         */
        public PatientDTO(Account account) {
            this.login = account.getLogin();
            this.id = account.getId();
            this.name = account.getFirstName();
            this.lastName = account.getLastName();
            this.email = account.getEmail();
            this.phoneNumber = account.getPhoneNumber();
            this.pesel = account.getPesel();
        }

        public String getLogin() {
            return login;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getPesel() {
            return pesel;
        }
    }
}
