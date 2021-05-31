package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Typ Receptionist data reprezentujący poziom dostępu konta aplikacji dla recepcjonisty.
 */
@Entity
@DiscriminatorValue("level.receptionist")
@NamedQueries({
        @NamedQuery(name = "ReceptionistData.findAll", query = "SELECT r FROM ReceptionistData r")})
public class ReceptionistData extends AccessLevel implements Serializable {

    /**
     * Tworzy nową instancję klasy Receptionist data.
     *
     * @param account account
     * @param active  active
     */
    public ReceptionistData(Account account, Boolean active) {
        super(account, active);
    }

    /**
     * Tworzy nową instancję klasy Receptionist data.
     */
    public ReceptionistData() {
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.ReceptionistData[ id=" + this.getId() + " ]";
    }

}
