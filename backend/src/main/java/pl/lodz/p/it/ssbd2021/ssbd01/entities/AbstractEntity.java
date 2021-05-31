package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Typ Abstract entity - klasa abstrakcyjnej encji.
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @Column(name = "version")
    @Version
    private Long version;
    @Basic(optional = false)
    @Column(name = "creation_date_time", nullable = false, updatable = false)
    @NotNull
    private LocalDateTime creationDateTime;
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    @NotNull
    private Account createdBy;
    @Column(name = "modification_date_time")
    private LocalDateTime modificationDateTime;
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    @ManyToOne
    private Account modifiedBy;
    @Column(name = "created_by_ip", length = 256)
    @Size(min = 0, max = 256)
    private String createdByIp;
    @Column(name = "modified_by_ip", length = 256)
    @Size(min = 0, max = 256)
    private String modifiedByIp;

    /**
     * Tworzy nową instancję klasy AbstractEntity.
     */
    public AbstractEntity() {
    }

    /**
     * Tworzy nową instancję klasy AbstractEntity.
     *
     * @param createdBy konto użytkownika tworzącego encję
     */
    public AbstractEntity(Account createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Pobiera pole created by ip.
     *
     * @return created by ip - adres ip użytkownika, który stworzył encję
     */
    public String getCreatedByIp() {
        return createdByIp;
    }

    /**
     * Ustawia pole created by ip.
     *
     * @param createdByIp created by ip - adres ip użytkownika, który stworzył encję
     */
    public void setCreatedByIp(String createdByIp) {
        this.createdByIp = createdByIp;
    }

    /**
     * Pobiera pole modified by ip.
     *
     * @return modified by ip - adres ip użytkownika, który modyfikował encję
     */
    public String getModifiedByIp() {
        return modifiedByIp;
    }

    /**
     * Ustawia pole modified by ip.
     *
     * @param modifiedByIp modified by ip - adres ip użytkownika, który modyfikował encję
     */
    public void setModifiedByIp(String modifiedByIp) {
        this.modifiedByIp = modifiedByIp;
    }

    /**
     * Pobiera wartośc pola ID.
     *
     * @return ID
     */
    public abstract Long getId();

    @PrePersist
    private void init() {
        creationDateTime = LocalDateTime.now();
    }

    @PreUpdate
    private void initUpdate() {
        modificationDateTime = LocalDateTime.now();
    }

    /**
     * Pobiera pole version.
     *
     * @return version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Ustawia pole version.
     *
     * @param version version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * Pobiera pole creation date time.
     *
     * @return creation date time
     */
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    /**
     * Pobiera pole modification date time.
     *
     * @return modification date time
     */
    public LocalDateTime getModificationDateTime() {
        return modificationDateTime;
    }

    /**
     * Ustawia pole modification date time.
     *
     * @param modificationDateTime modification date time
     */
    public void setModificationDateTime(LocalDateTime modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
    }

    /**
     * Pobiera pole created by.
     *
     * @return created by
     */
    public Account getCreatedBy() {
        return createdBy;
    }

    /**
     * Ustawia pole created by.
     *
     * @param createdBy created by
     */
    public void setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Pobiera pole modified by.
     *
     * @return modified by
     */
    public Account getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Ustawia pole modified by.
     *
     * @param modifiedBy modified by
     */
    public void setModifiedBy(Account modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() != this.getClass()) {
            return false;
        }
        AbstractEntity other = (AbstractEntity) object;
        return (this.getId() != null || other.getId() == null) && (this.getId() == null || this.getId().equals(other.getId()));
    }
}