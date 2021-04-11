package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Typ Admin data reprezentujący poziom dostępu konta aplikacji dla admina.
 */
@Entity
@DiscriminatorValue("level.admin")
public class AdminData extends AccessLevel implements Serializable {

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData[ id=" + this.getId() + " ]";

    }

}
