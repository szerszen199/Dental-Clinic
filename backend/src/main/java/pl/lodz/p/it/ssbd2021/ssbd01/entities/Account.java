package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import org.hibernate.validator.constraints.pl.PESEL;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
 * Typ Account - klasa reprezentująca encję konta.
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
        @NamedQuery(name = "Account.findByEmailRecall", query = "SELECT a FROM Account a WHERE a.emailRecall = :emailrecall"),
        @NamedQuery(name = "Account.findByFirstPasswordChange", query = "SELECT a FROM Account a WHERE a.firstPasswordChange = :firstPasswordChange"),
        @NamedQuery(name = "Account.findByLanguage", query = "SELECT a FROM Account a WHERE a.language = :language"),
        @NamedQuery(name = "Account.findByVersion", query = "SELECT a FROM Account a WHERE a.version = :version"),
        @NamedQuery(name = "Account.findByLoginOrEmailOrPesel", query = "SELECT a FROM Account a WHERE a.login = :login OR a.email = :email OR a.pesel = :pesel"),
        @NamedQuery(name = "Account.findByAccessLevel", query = "SELECT a FROM Account a, AccessLevel al WHERE al.accountId.id = a.id and al.level = :level and a.enabled = true and a.active = true"),
        @NamedQuery(name = "Account.findByLogin", query = "SELECT a FROM Account a WHERE a.login = :login")})
public class Account extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "accountId")
    private final Set<AccessLevel> accessLevels = new HashSet<>();
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
    @PESEL
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

    @Column(name = "last_block_unlock_ip", length = 256)
    @Size(min = 0, max = 256)
    private String lastBlockUnlockIp;

    @Column(name = "last_unsuccessful_login")
    private LocalDateTime lastUnsuccessfulLogin;

    @Column(name = "last_block_unlock_date_time")
    private LocalDateTime lastBlockUnlockDateTime;

    @Column(name = "last_unsuccessful_login_ip", length = 256)
    @Size(min = 7, max = 256)
    private String lastUnsuccessfulLoginIp;

    @Column(name = "unsuccessful_login_count_since_last_login")
    @Min(0)
    private Integer unsuccessfulLoginCounter = 0;

    @Column(name = "language", columnDefinition = "bpchar", length = 2, nullable = false)
    @Size(min = 2, max = 2)
    @NotNull
    private String language;

    @Basic(optional = false)
    @Column(name = "email_recall", nullable = false)
    @NotNull
    private Boolean emailRecall = false;

    @Basic(optional = false)
    @Column(name = "first_password_change", nullable = false)
    @NotNull
    private Boolean firstPasswordChange = false;

    @JoinColumn(name = "last_block_unlock_modified_by", referencedColumnName = "id")
    @ManyToOne
    private Account lastBlockUnlockModifiedBy;

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
     * @param login       login konta
     * @param email       adres e-mail przypisany do konta
     * @param firstName   imię użytkownika
     * @param lastName    nazwisko użytkownika
     * @param phoneNumber numer telefonu
     * @param pesel       the pesel
     * @param language    język
     */
    public Account(String login, String email, String firstName, String lastName, String phoneNumber, String pesel, String language) {
        this.login = login;
        this.email = email;
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
     * @param language    język
     */
    public Account(long id, String login, String email, String password, String firstName, String lastName, String phoneNumber, String pesel, String language) {
        this.id = id;
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
     * Pobiera pole last block unlock date time.
     *
     * @return last block unlock date time
     */
    public LocalDateTime getLastBlockUnlockDateTime() {
        return lastBlockUnlockDateTime;
    }

    /**
     * Ustawia pole last block unlock date time.
     *
     * @param lastBlockUnlockDateTime last block unlock date time
     */
    public void setLastBlockUnlockDateTime(LocalDateTime lastBlockUnlockDateTime) {
        this.lastBlockUnlockDateTime = lastBlockUnlockDateTime;
    }

    /**
     * Pobiera pole last block unlock modified by.
     *
     * @return last block unlock modified by
     */
    public Account getLastBlockUnlockModifiedBy() {
        return lastBlockUnlockModifiedBy;
    }

    /**
     * Ustawia pole last block unlock modified by.
     *
     * @param lastBlockUnlockModifiedBy last block unlock modified by
     */
    public void setLastBlockUnlockModifiedBy(Account lastBlockUnlockModifiedBy) {
        this.lastBlockUnlockModifiedBy = lastBlockUnlockModifiedBy;
    }

    /**
     * Pobiera pole last block unlock ip.
     *
     * @return last block unlock ip
     */
    public String getLastBlockUnlockIp() {
        return lastBlockUnlockIp;
    }

    /**
     * Ustawia pole last block unlock ip.
     *
     * @param lastBlockUnlockIp last block unlock ip
     */
    public void setLastBlockUnlockIp(String lastBlockUnlockIp) {
        this.lastBlockUnlockIp = lastBlockUnlockIp;
    }

    @Override
    public Long getId() {
        return id;
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
     * Pobiera pole email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ustawia pole email.
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Zwraca prawdę gdy ustawiony był ciemny motyw, fałsz gdy był ustawiony jasny motyw.
     *
     * @return boolean
     */
    public boolean isDarkMode() {
        return isDarkMode;
    }

    /**
     * Ustawia pole dark mode.
     *
     * @param darkMode dark mode - ciemny motyw
     */
    public void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
    }

    /**
     * Pobiera pole password.
     *
     * @return password hasło
     */
    public String getPassword() {
        return password;
    }

    /**
     * Ustawia pole password.
     *
     * @param password password hasło
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Pobiera pole access levels.
     *
     * @return access levels poziomy dostępu
     */
    public Set<AccessLevel> getAccessLevels() {
        return accessLevels;
    }

    /**
     * Pobiera pole first name.
     *
     * @return first name imie
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Ustawia pole first name.
     *
     * @param firstName first name imie
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Pobiera pole last name.
     *
     * @return last name nazwisko
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Ustawia pole last name.
     *
     * @param lastName last name nazwisko
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Pobiera pole phone number.
     *
     * @return phone number nuemr telefonu
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Ustawia pole phone number.
     *
     * @param phoneNumber phone number numer telefonu
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Pobiera pole pesel.
     *
     * @return pesel
     */
    public String getPesel() {
        return pesel;
    }

    /**
     * Ustawia pole pesel.
     *
     * @param pesel pesel
     */
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    /**
     * Pobiera pole active.
     *
     * @return active czy jest aktywne konto
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Ustawia pole active.
     *
     * @param active active czy jest aktywne konto
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Pobiera pole enabled.
     *
     * @return enabled czy konto jest włączone
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Ustawia pole enabled.
     *
     * @param enabled enabled czy konto jest włączone
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Pobiera pole last successful login.
     *
     * @return last successful login data ostatniego poprawneog logowania
     */
    public LocalDateTime getLastSuccessfulLogin() {
        return lastSuccessfulLogin;
    }

    /**
     * Ustawia pole last successful login.
     *
     * @param lastSuccessfulLogin last successful login data ostatniego poprawneog logowania
     */
    public void setLastSuccessfulLogin(LocalDateTime lastSuccessfulLogin) {
        this.lastSuccessfulLogin = lastSuccessfulLogin;
    }

    /**
     * Pobiera pole last successful login ip.
     *
     * @return last successful login ip adres ip ostantiego poprawnego logowania
     */
    public String getLastSuccessfulLoginIp() {
        return lastSuccessfulLoginIp;
    }

    /**
     * Ustawia pole last successful login ip.
     *
     * @param lastSuccessfulLoginIp last successful login ip adres ip ostantiego poprawnego logowania
     */
    public void setLastSuccessfulLoginIp(String lastSuccessfulLoginIp) {
        this.lastSuccessfulLoginIp = lastSuccessfulLoginIp;
    }

    /**
     * Pobiera pole last unsuccessful login.
     *
     * @return last unsuccessful login  data ostatniego niepoprawnego logowania
     */
    public LocalDateTime getLastUnsuccessfulLogin() {
        return lastUnsuccessfulLogin;
    }

    /**
     * Ustawia pole last unsuccessful login.
     *
     * @param lastUnsuccessfulLogin last unsuccessful login  data ostatniego niepoprawnego logowania
     */
    public void setLastUnsuccessfulLogin(LocalDateTime lastUnsuccessfulLogin) {
        this.lastUnsuccessfulLogin = lastUnsuccessfulLogin;
    }

    /**
     * Pobiera pole last unsuccessful login ip.
     *
     * @return last unsuccessful login ip adres ip ostantiego niepoprawnego logowania
     */
    public String getLastUnsuccessfulLoginIp() {
        return lastUnsuccessfulLoginIp;
    }

    /**
     * Ustawia pole last unsuccessful login ip.
     *
     * @param lastUnsuccessfulLoginIp last unsuccessful login ip adres ip ostantiego niepoprawnego logowania
     */
    public void setLastUnsuccessfulLoginIp(String lastUnsuccessfulLoginIp) {
        this.lastUnsuccessfulLoginIp = lastUnsuccessfulLoginIp;
    }

    /**
     * Pobiera pole unsuccessful login counter.
     *
     * @return unsuccessful login counter licznik niepoprawnych logowań
     */
    public Integer getUnsuccessfulLoginCounter() {
        return unsuccessfulLoginCounter;
    }

    /**
     * Ustawia pole unsuccessful login counter.
     *
     * @param unsuccessfulLoginCounter unsuccessful login counter licznik niepoprawnych logowań
     */
    public void setUnsuccessfulLoginCounter(Integer unsuccessfulLoginCounter) {
        this.unsuccessfulLoginCounter = unsuccessfulLoginCounter;
    }

    /**
     * Pobiera pole email recall.
     *
     * @return email recall
     */
    public Boolean getEmailRecall() {
        return emailRecall;
    }

    /**
     * Ustawia pole email recall.
     *
     * @param emailrecall emailrecall
     */
    public void setEmailRecall(Boolean emailrecall) {
        this.emailRecall = emailrecall;
    }

    /**
     * Pobiera pole language - aktualnie wybrany język.
     *
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Ustawia pole language - aktualnie wybrany język.
     *
     * @param language language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Pobiera pole first password change.
     *
     * @return first password change
     */
    public Boolean getFirstPasswordChange() {
        return firstPasswordChange;
    }

    /**
     * Ustawia pole first password change.
     *
     * @param firstPasswordChange first password change
     */
    public void setFirstPasswordChange(Boolean firstPasswordChange) {
        this.firstPasswordChange = firstPasswordChange;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.Account[ id=" + id + " ]";
    }

}
