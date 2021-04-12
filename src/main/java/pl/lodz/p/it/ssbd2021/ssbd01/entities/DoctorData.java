package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * Typ Doctor data reprezentujący poziom dostępu konta aplikacji dla doktora.
 */
@Entity
@DiscriminatorValue("level.doctor")
@NamedQueries({
        @NamedQuery(name = "DoctorData.findAll", query = "SELECT d FROM DoctorData d")})
public class DoctorData extends AccessLevel implements Serializable {

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorData[ id=" + this.getId() + " ]";
    }
}
