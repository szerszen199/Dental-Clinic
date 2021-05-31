package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Typ Doctor data reprezentujący poziom dostępu konta aplikacji dla doktora.
 */
@Entity
@DiscriminatorValue("level.doctor")
@NamedQueries({
        @NamedQuery(name = "DoctorData.findAll", query = "SELECT d FROM DoctorData d")})
public class DoctorData extends AccessLevel implements Serializable {

    /**
     * Tworzy nową instancję klasy Doctor data.
     *
     * @param account account
     * @param active  active
     */
    public DoctorData(Account account, Boolean active) {
        super(account, active);
    }

    /**
     * Tworzy nową instancję klasy Doctor data.
     */
    public DoctorData() {
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorData[ id=" + this.getId() + " ]";
    }
}
