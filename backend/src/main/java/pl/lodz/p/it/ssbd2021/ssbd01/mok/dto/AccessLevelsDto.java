package pl.lodz.p.it.ssbd2021.ssbd01.mok.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import pl.lodz.p.it.ssbd2021.ssbd01.common.Levels;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AdminData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.ReceptionistData;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AccessLevelException;

/**
 * Klasa reprezentuje zbiór poziomów dostępu przypisanych do konta wraz z loginem tego konta.
 */
public class AccessLevelsDto {

    @Size(min = 1, max = 4)
    @NotNull
    private Set<String> accessLevels;

    private String login;

    private Long version;

    /**
     * Tworzy nową instancję klasy  AccessLevelDto.
     *
     * @param account konto, dla którego poziomy dostępu mają zostać przekształcone w DTO
     */
    public AccessLevelsDto(Account account) {
        accessLevels = account.getAccessLevels().stream().map(AccessLevel::getLevel).collect(Collectors.toSet());
        login = account.getLogin();
        version = account.getVersion();
    }

    /**
     *Tworzy nową instancję klasy  AccessLevelDto.
     */
    public AccessLevelsDto() {
    }

    /**
     * Mapuje zbiór nazw poziomów dostępu na zbiór ich obiektów.
     *
     * @return zbiór obiektów poziomów dostępu
     * @throws AccessLevelException wyjątek w przypadku nie znalezienia poziomu dostępu
     */
    public Set<AccessLevel> toAccessLevelsSet() throws AccessLevelException {
        Set<AccessLevel> accessLevelsSet = new HashSet<>();
        for (String level : accessLevels) {
            accessLevelsSet.add(this.toAccessLevel(level));
        }
        return accessLevelsSet;
    }

    /**
     * Mapuje nazwę poziomu dostepu na jego obiekt.
     *
     * @param level nazwa poziomu dostępu
     * @return  obiekt poziomu dostępu
     * @throws AccessLevelException wyjątek w przypadku nie znalezienia poziomu dostępu
     */
    public AccessLevel toAccessLevel(String level) throws AccessLevelException {
        if (Levels.ADMINISTRATOR.getLevel().equals(level)) {
            return new AdminData();
        } else if (Levels.DOCTOR.getLevel().equals(level)) {
            return new DoctorData();
        } else if (Levels.RECEPTIONIST.getLevel().equals(level)) {
            return new ReceptionistData();
        } else if (Levels.PATIENT.getLevel().equals(level)) {
            return new PatientData();
        }
        throw AccessLevelException.noSuchAccessLevel();
    }

    public Set<String> getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(Set<String> accessLevels) {
        this.accessLevels = accessLevels;
    }

    public String getLogin() {
        return login;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "AccessLevelsDto{"
                + "accessLevels=" + accessLevels
                + ", login='" + login + '\''
                + ", version=" + version
                + '}';
    }
}
