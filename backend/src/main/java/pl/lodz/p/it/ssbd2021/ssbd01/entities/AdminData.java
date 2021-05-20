package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * Typ Admin data reprezentujący poziom dostępu konta aplikacji dla admina.
 */
@Entity
@DiscriminatorValue("level.administrator")
@NamedQueries({
        @NamedQuery(name = "AdminData.findAll", query = "SELECT a FROM AdminData a")})
public class AdminData extends AccessLevel implements Serializable {

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.AdminData[ id=" + this.getId() + " ]";
    }

}
