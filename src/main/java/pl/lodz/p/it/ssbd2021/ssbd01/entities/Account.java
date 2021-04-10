package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "accounts", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"}),
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
    @NamedQuery(name = "Account.findByModificationDate", query = "SELECT a FROM Account a WHERE a.modificationDate = :modificationDate"),
    @NamedQuery(name = "Account.findByCreationDate", query = "SELECT a FROM Account a WHERE a.creationDate = :creationDate"),
    @NamedQuery(name = "Account.findByLanguage", query = "SELECT a FROM Account a WHERE a.language = :language"),
    @NamedQuery(name = "Account.findByVersion", query = "SELECT a FROM Account a WHERE a.version = :version")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_generator")
    @SequenceGenerator(name = "accounts_generator", sequenceName = "accounts_seq")
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    
    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private Collection<AccessLevel> accessLevels;
    
    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    @Basic(optional = false)
    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;
    
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
    
    @Column(name = "pesel", length = 11)
    private String pesel;
    
    @Basic(optional = false)
    @Column(name = "active", nullable = false)
    private boolean active;
    
    @Basic(optional = false)
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    
    @Column(name = "last_successful_login")
    private LocalDateTime lastSuccessfulLogin;
    
    @Column(name = "last_successful_login_ip", length = 15)
    private String lastSuccessfulLoginIp;
    
    @Column(name = "last_unsuccessful_login")
    private LocalDateTime lastUnsuccessfulLogin;
    
    @Column(name = "last_unsuccessful_login_ip", length = 15)
    private String lastUnsuccessfulLoginIp;
    
    @Column(name = "unsuccessful_login_count_since_last_login")
    private Integer unsuccessfulLoginCounter;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Account createdBy;
    
    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    @ManyToOne
    private Account modifiedBy;
    
    @Column(name = "language", length = 2)
    private String language;
    
    @Column(name = "version")
    private Long version;
    
    public Account() {
    }

    public Account(Long id) {
        this.id = id;
    }

    public Account(Long id, String email, String password, String firstName, String lastName, boolean active, boolean enabled) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
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

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Collection<AccessLevel> getAccessLevelCollection() {
        return accessLevels;
    }

    public void setAccessLevelCollection(Collection<AccessLevel> accessLevelCollection) {
        this.accessLevels = accessLevelCollection;
    }

    public Account getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Account modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Account getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.Account[ id=" + id + " ]";
    }
    
}
