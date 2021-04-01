package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "accounts", schema = "public", catalog = "ssbd01")
public class Account {
    
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @OneToMany(mappedBy = "accountsByAccountId")
    private Collection<AccessLevel> accessLevels;

    @Basic(optional = false)
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Basic(optional = false)
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;

    @Basic(optional = false)
    @Column(name = "phone_number", nullable = true, length = 15)
    private String phoneNumber;

    @Basic()
    @Column(name = "pesel", nullable = true, length = 11)
    private String pesel;

    @Column(name = "last_successful_login", nullable = true)
    private LocalDateTime lastSuccessfulLogin;

    @Basic(optional = false)
    @Column(name = "last_successful_login_ip", nullable = true, length = 15)
    private String lastSuccessfulLoginIp;

    @Column(name = "last_unsuccessful_login", nullable = true)
    private LocalDateTime lastUnsuccessfulLogin;

    @Basic(optional = false)
    @Column(name = "last_unsuccessful_login_ip", nullable = true, length = 15)
    private String lastUnsuccessfulLoginIp;

    @Basic(optional = false)
    @Column(name = "unsuccessful_login_count_since_last_login", nullable = true)
    private Integer unsuccessfulLoginCounter;

    @Column(name = "creation_date", nullable = true)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    private Account createdBy;

    @Column(name = "modification_date", nullable = true)
    private LocalDateTime modificationDate;

    @ManyToOne
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private Account modifiedBy;

    @Basic
    @Column(name = "language", nullable = true, length = 2)
    private String language;

    @Basic(optional = false)
    @Column(name = "version", nullable = true)
    private Long version;


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

    public Collection<AccessLevel> getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(Collection<AccessLevel> accessLevels) {
        this.accessLevels = accessLevels;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Account getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Account getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Account modifiedBy) {
        this.modifiedBy = modifiedBy;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Account account = (Account) o;

        return new EqualsBuilder().append(id, account.id).append(email, account.email).append(password, account.password).append(firstName, account.firstName).append(lastName, account.lastName).append(phoneNumber, account.phoneNumber).append(pesel, account.pesel).append(active, account.active).append(enabled, account.enabled).append(lastSuccessfulLogin, account.lastSuccessfulLogin).append(lastSuccessfulLoginIp, account.lastSuccessfulLoginIp).append(lastUnsuccessfulLogin, account.lastUnsuccessfulLogin).append(lastUnsuccessfulLoginIp, account.lastUnsuccessfulLoginIp).append(unsuccessfulLoginCounter, account.unsuccessfulLoginCounter).append(modificationDate, account.modificationDate).append(creationDate, account.creationDate).append(language, account.language).append(version, account.version).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(email).append(password).append(firstName).append(lastName).append(phoneNumber).append(pesel).append(active).append(enabled).append(lastSuccessfulLogin).append(lastSuccessfulLoginIp).append(lastUnsuccessfulLogin).append(lastUnsuccessfulLoginIp).append(unsuccessfulLoginCounter).append(modificationDate).append(creationDate).append(language).append(version).toHashCode();
    }
}
