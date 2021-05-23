package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Typ Account.
 */
@Entity
@Table(name = "accounts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"login"}),
        @UniqueConstraint(columnNames = {"pesel"})})
@NamedQueries({
        @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
        @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
        @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email"),
        @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
        @NamedQuery(name = "Account.findByFirstName", query = "SELECT a FROM Account a WHERE a.firstName = :firstName"),
        @NamedQuery(name = "Account.findByLastName", query = "SELECT a FROM Account a WHERE a.lastName = :lastName"),
        @NamedQuery(name = "Account.findByPhoneNumber", query = "SELECT a FROM Account a WHERE a.phoneNumber = :phoneNumber"),
        @NamedQuery(name = "Account.findByPesel", query = "SELECT a FROM Account a WHERE a.pesel = :pesel"),
        @NamedQuery(name = "Account.findByActive", query = "SELECT a FROM Account a WHERE a.active = :active"),
        @NamedQuery(name = "Account.findByEnabled", query = "SELECT a FROM Account a WHERE a.enabled = :enabled"),
        @NamedQuery(name = "Account.findByLastSuccessfulLogin", query = "SELECT a FROM Account a WHERE a.lastSuccessfulLogin = :lastSuccessfulLogin"),
        @NamedQuery(name = "Account.findByLastSuccessfulLoginIp", query = "SELECT a FROM Account a WHERE a.lastSuccessfulLoginIp = :lastSuccessfulLoginIp"),
        @NamedQuery(name = "Account.findByLastUnsuccessfulLogin", query = "SELECT a FROM Account a WHERE a.lastUnsuccessfulLogin = :lastUnsuccessfulLogin"),
        @NamedQuery(name = "Account.findByLastUnsuccessfulLoginIp", query = "SELECT a FROM Account a WHERE a.lastUnsuccessfulLoginIp = :lastUnsuccessfulLoginIp"),
        @NamedQuery(name = "Account.findByUnsuccessfulLoginCountSinceLastLogin", query = "SELECT a FROM Account a WHERE a.unsuccessfulLoginCounter = :unsuccessfulLoginCountSinceLastLogin"),
        @NamedQuery(name = "Account.findByModificationDate", query = "SELECT a FROM Account a WHERE a.modificationDateTime = :modificationDate"),
        @NamedQuery(name = "Account.findByCreationDate", query = "SELECT a FROM Account a WHERE a.creationDateTime = :creationDate"),
        @NamedQuery(name = "Account.findByLanguage", query = "SELECT a FROM Account a WHERE a.language = :language"),
        @NamedQuery(name = "Account.findByVersion", query = "SELECT a FROM Account a WHERE a.version = :version"),
        @NamedQuery(name = "Account.findByLogin", query = "SELECT a FROM Account a WHERE a.login = :login")})
public class Account extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_generator")
    @SequenceGenerator(name = "accounts_generator", sequenceName = "accounts_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", updatable = false, nullable = false)
    @NotNull
    private Long id;

    @Basic(optional = false)
    @Column(name = "login", updatable = false, nullable = false, length = 60)
    @NotNull
    @Login
    private String login;

    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 100)
    @NotNull
    @Email
    @Size(min = 4, max = 100)
    private String email;

    @Basic(optional = true)
    @Column(name = "is_dark_mode", nullable = true)
    private boolean isDarkMode = false;

    @Basic(optional = false)
    @Column(name = "password", columnDefinition = "bpchar", nullable = false, length = 64)
    @NotNull
    @Size(min = 64, max = 64)
    private String password;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Set<AccessLevel> accessLevels = new HashSet<>();

    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name", nullable = false, length = 80)
    @NotNull
    @Size(min = 1, max = 80)
    private String lastName;

    @Column(name = "phone_number", length = 15)
    @Size(min = 9, max = 15)
    private String phoneNumber;

    @Column(name = "pesel", columnDefinition = "bpchar", length = 11)
    @Size(min = 11, max = 11)
    private String pesel;

    @Basic(optional = false)
    @Column(name = "active", nullable = false)
    @NotNull
    private Boolean active = true;

    @Basic(optional = false)
    @Column(name = "enabled", nullable = false)
    @NotNull
    private Boolean enabled = false;

    @Column(name = "last_successful_login")
    private LocalDateTime lastSuccessfulLogin;

    @Column(name = "last_successful_login_ip", length = 256)
    @Size(min = 0, max = 256)
    private String lastSuccessfulLoginIp;

    @Column(name = "last_unsuccessful_login")
    private LocalDateTime lastUnsuccessfulLogin;

    @Column(name = "last_unsuccessful_login_ip", length = 256)
    @Size(min = 7, max = 256)
    private String lastUnsuccessfulLoginIp;

    @Column(name = "unsuccessful_login_count_since_last_login")
    @Min(0)
    private Integer unsuccessfulLoginCounter = 0;

    @Column(name = "language", columnDefinition = "bpchar", length = 2, nullable = false)
    @Size(min = 2, max = 2)
    private String language;

    /**
     * Tworzy nową instancję klasy Account.
     */
    public Account() {
    }

    /**
     * Tworzy nową instancję klasy Account reprezentujacej konto użytkownika aplikacji.
     *
     * @param login       login konta
     * @param email       adres e-mail przypisany do konta
     * @param password    hasło konta
     * @param firstName   imię użytkownika
     * @param lastName    nazwisko użytkownika
     * @param phoneNumber numer telefonu
     * @param pesel       pesel
     */
    public Account(String login, String email, String password, String firstName, String lastName, String phoneNumber, String pesel) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
    }

    /**
     * Tworzy nową instancję klasy Account reprezentujacej konto użytkownika aplikacji.
     *
     * @param login       login konta
     * @param email       adres e-mail przypisany do konta
     * @param password    hasło konta
     * @param firstName   imię użytkownika
     * @param lastName    nazwisko użytkownika
     * @param phoneNumber numer telefonu
     * @param pesel       pesel
     * @param language    język
     */
    public Account(String login, String email, String password, String firstName, String lastName, String phoneNumber, String pesel, String language) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
        this.language = language;
    }

    /**
     * Tworzy nową instancję klasy Account reprezentujacej konto użytkownika aplikacji.
     *
     * @param id          id konta
     * @param login       login konta
     * @param email       adres e-mail przypisany do konta
     * @param password    hasło konta
     * @param firstName   imię użytkownika
     * @param lastName    nazwisko użytkownika
     * @param phoneNumber numer telefonu
     * @param pesel       pesel
     */
    public Account(long id, String login, String email, String password, String firstName, String lastName, String phoneNumber, String pesel) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDarkMode() {
        return isDarkMode;
    }

    public void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<AccessLevel> getAccessLevels() {
        return accessLevels;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getLastSuccessfulLogin() {
        return lastSuccessfulLogin;
    }

    public void setLastSuccessfulLogin(LocalDateTime lastSuccessfulLogin) {
        this.lastSuccessfulLogin = lastSuccessfulLogin;
    }

    public String getLastSuccessfulLoginIp() {
        return lastSuccessfulLoginIp;
    }

    public void setLastSuccessfulLoginIp(String lastSuccessfulLoginIp) {
        this.lastSuccessfulLoginIp = lastSuccessfulLoginIp;
    }

    public LocalDateTime getLastUnsuccessfulLogin() {
        return lastUnsuccessfulLogin;
    }

    public void setLastUnsuccessfulLogin(LocalDateTime lastUnsuccessfulLogin) {
        this.lastUnsuccessfulLogin = lastUnsuccessfulLogin;
    }

    public String getLastUnsuccessfulLoginIp() {
        return lastUnsuccessfulLoginIp;
    }

    public void setLastUnsuccessfulLoginIp(String lastUnsuccessfulLoginIp) {
        this.lastUnsuccessfulLoginIp = lastUnsuccessfulLoginIp;
    }

    public Integer getUnsuccessfulLoginCounter() {
        return unsuccessfulLoginCounter;
    }

    public void setUnsuccessfulLoginCounter(Integer unsuccessfulLoginCounter) {
        this.unsuccessfulLoginCounter = unsuccessfulLoginCounter;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.Account[ id=" + id + " ]";
    }
}
