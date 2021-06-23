package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Typ Admin data reprezentujący poziom dostępu konta aplikacji dla admina.
 */
@Entity
@DiscriminatorValue("level.administrator")
@NamedQueries({
        @NamedQuery(name = "AdminData.findAll", query = "SELECT a FROM AdminData a")})
public class AdminData extends AccessLevel implements Serializable {

    /**
     * Tworzy nową instancję klasy Admin data.
     */
    public AdminData() {
    }

    /**
     * Tworzy nową instancję klasy Admin data.
     *
     * @param account account
     * @param active  active
     */
    public AdminData(Account account, Boolean active) {
        super(account, active);
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.AdminData[ id=" + this.getId() + " ]";
    }

}
