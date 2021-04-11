package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Typ Doctor data reprezentujący poziom dostępu konta aplikacji dla recepcjonisty.
 */
@Entity
@DiscriminatorValue("level.receptionist")
public class ReceptionistData extends AccessLevel implements Serializable {

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.ReceptionistData[ id=" + this.getId() + " ]";
    }

}
