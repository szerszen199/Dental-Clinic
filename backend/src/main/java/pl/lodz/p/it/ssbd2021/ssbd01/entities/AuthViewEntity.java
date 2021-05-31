package pl.lodz.p.it.ssbd2021.ssbd01.entities;


import net.jcip.annotations.Immutable;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Typ Auth view entity - klasa widoku.
 */
// Nie dziedzicy bo abstrakcyjnej bo nie posiada takich pól
@Entity
@Immutable
@Table(name = "glassfish_auth_view")
@NamedQueries({
        @NamedQuery(name = "AuthViewEntity.findAll", query = "SELECT a FROM AuthViewEntity a"),
        @NamedQuery(name = "AuthViewEntity.findByLogin", query = "SELECT a FROM AuthViewEntity a where a.login = :login")})
public class AuthViewEntity {

    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false, insertable = false)
    @NotNull
    private Long id;

    @Basic(optional = false)
    @Column(name = "login", updatable = false, nullable = false, length = 60)
    @NotNull
    @Login
    private String login;

    @Basic(optional = false)
    @Column(name = "password", columnDefinition = "bpchar", nullable = false, length = 64)
    @NotNull
    @Size(min = 64, max = 64)
    private String password;

    @Basic(optional = false)
    @Column(name = "level", nullable = false, length = 32, updatable = false, insertable = false)
    @NotNull
    @Size(min = 7, max = 32)
    private String level;

    /**
     * Tworzy nową instancję klasy Auth view entity.
     */
    public AuthViewEntity() {
    }

    /**
     * Tworzy nową instancję klasy Auth view entity.
     *
     * @param login    login
     * @param password password
     * @param level    level
     */
    public AuthViewEntity(String login, String password, String level) {
        this.login = login;
        this.password = password;
        this.level = level;
    }


    /**
     * Pobiera pole login.
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Pobiera pole password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Pobiera pole level.
     *
     * @return level
     */
    public String getLevel() {
        return level;
    }

}
