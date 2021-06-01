package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;


/**
 * Typ Patient data reprezentujący poziom dostępu konta aplikacji dla pacjenta.
 */
@Entity
@DiscriminatorValue("level.patient")
@NamedQueries({
        @NamedQuery(name = "PatientData.findAll", query = "SELECT p FROM PatientData p")})
public class PatientData extends AccessLevel implements Serializable {
    /**
     * Tworzy nową instancję klasy Patient data.
     */
    public PatientData() {
    }

    /**
     * Tworzy nową instancję klasy Patient data.
     *
     * @param account account
     * @param active  active
     */
    public PatientData(Account account, Boolean active) {
        super(account, active);
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData[ id=" + this.getId() + " ]";
    }

}
