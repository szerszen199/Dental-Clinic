package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


/**
 * Typ Patient data reprezentujący poziom dostępu konta aplikacji dla pacjenta.
 */
@Entity
@DiscriminatorValue("level.patient")
@NamedQueries({
        @NamedQuery(name = "PatientData.findAll", query = "SELECT p FROM PatientData p")})
public class PatientData extends AccessLevel implements Serializable {

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData[ id=" + this.getId() + " ]";
    }

}
