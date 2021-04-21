package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class AbstractEntity {

    /**
     * Pobiera wartośc pola ID.
     *
     * @return ID
     */
    public abstract Long getId();

    @Column(name = "version")
    private Long version;

    @Basic(optional = false)
    @Column(name = "creation_date_time", nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private Account createdBy;

    @Column(name = "modification_date_time")
    private LocalDateTime modificationDateTime;

    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    @ManyToOne
    private Account modifiedBy;

    @PrePersist
    private void init() {
        creationDateTime = LocalDateTime.now();
    }

    @PreUpdate
    private void initUpdate() {
        modificationDateTime = LocalDateTime.now();
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

    public LocalDateTime getModificationDateTime() {
        return modificationDateTime;
    }

    public void setModificationDateTime(LocalDateTime modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
    }

    public Account getCreatedBy() {
        return createdBy;
    }

    public Account getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Account modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
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