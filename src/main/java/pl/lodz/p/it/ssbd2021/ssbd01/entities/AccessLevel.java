package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * Typ Access level reprezentujący poziom dostępu konta aplikacji.
 */
@Entity
@Table(name = "access_levels", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"level", "account_id"})})
@NamedQueries({
        @NamedQuery(name = "AccessLevel.findAll", query = "SELECT a FROM AccessLevel a"),
        @NamedQuery(name = "AccessLevel.findById", query = "SELECT a FROM AccessLevel a WHERE a.id = :id"),
        @NamedQuery(name = "AccessLevel.findByLevel", query = "SELECT a FROM AccessLevel a WHERE a.level = :level"),
        @NamedQuery(name = "AccessLevel.findByActive", query = "SELECT a FROM AccessLevel a WHERE a.active = :active"),
        @NamedQuery(name = "AccessLevel.findByVersion", query = "SELECT a FROM AccessLevel a WHERE a.version = :version"),
        @NamedQuery(name = "AccessLevel.findByCreationDateTime", query = "SELECT a FROM AccessLevel a WHERE a.creationDateTime = :creationDateTime"),
        @NamedQuery(name = "AccessLevel.findByModificationDateTime", query = "SELECT a FROM AccessLevel a WHERE a.modificationDateTime = :modificationDateTime")})
public class AccessLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "access_levels_generator")
    @SequenceGenerator(name = "access_levels_generator", sequenceName = "access_levels_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "account_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Account account;

    @Basic(optional = false)
    @Column(name = "level", nullable = false, length = 16)
    private String level;

    @Basic(optional = false)
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Basic(optional = false)
    @Column(name = "creation_date_time", nullable = false)
    private LocalDateTime creationDateTime;

    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Account createdBy;

    @Column(name = "modification_date_time")
    private LocalDateTime modificationDateTime;

    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    @OneToOne
    private Account modifiedBy;

    @Column(name = "version")
    private Long version;

    /**
     * Tworzy nową instancję klasy Access level.
     */
    public AccessLevel() {
    }

    /**
     * Tworzy nową instancję klasy AccessLevel.
     *
     * @param id klucz glowny
     */
    public AccessLevel(Long id) {
        this.id = id;
    }

    /**
     * Tworzy nową instancję klasy AccessLevel.
     *
     * @param id               klucz glowny
     * @param account          klucz obcy konto
     * @param level            nazwa poziomu dostepu
     * @param active           status
     * @param creationDateTime data stworzenia
     */
    public AccessLevel(Long id, Account account, String level, Boolean active, LocalDateTime creationDateTime) {
        this.id = id;
        this.account = account;
        this.level = level;
        this.active = active;
        this.creationDateTime = creationDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDateTime getModificationDateTime() {
        return modificationDateTime;
    }

    public void setModificationDateTime(LocalDateTime modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
    }

    public Account getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
    }

    public Account getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Account modifiedBy) {
        this.modifiedBy = modifiedBy;
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
        if (!(object instanceof AccessLevel)) {
            return false;
        }
        AccessLevel other = (AccessLevel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel[ id=" + id + " ]";
    }

}
