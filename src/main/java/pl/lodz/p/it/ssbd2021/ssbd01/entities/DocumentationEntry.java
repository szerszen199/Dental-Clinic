/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author student
 */
@Entity
@Table(name = "documentation_entries")
@NamedQueries({
    @NamedQuery(name = "DocumentationEntry.findAll", query = "SELECT d FROM DocumentationEntry d"),
    @NamedQuery(name = "DocumentationEntry.findById", query = "SELECT d FROM DocumentationEntry d WHERE d.id = :id"),
    @NamedQuery(name = "DocumentationEntry.findByWasDone", query = "SELECT d FROM DocumentationEntry d WHERE d.wasDone = :wasDone"),
    @NamedQuery(name = "DocumentationEntry.findByToBeDone", query = "SELECT d FROM DocumentationEntry d WHERE d.toBeDone = :toBeDone"),
    @NamedQuery(name = "DocumentationEntry.findByVersion", query = "SELECT d FROM DocumentationEntry d WHERE d.version = :version"),
    @NamedQuery(name = "DocumentationEntry.findByCreationDateTime", query = "SELECT d FROM DocumentationEntry d WHERE d.creationDateTime = :creationDateTime"),
    @NamedQuery(name = "DocumentationEntry.findByModificationDateTime", query = "SELECT d FROM DocumentationEntry d WHERE d.modificationDateTime = :modificationDateTime")})
public class DocumentationEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "was_done")
    private String wasDone;

    @Column(name = "to_be_done")
    private String toBeDone;

    @Basic(optional = false)
    @Column(name = "creation_date_time")
    private Long creationDateTime;

    @Column(name = "modification_date_time")
    private Long modificationDateTime;

    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account createdBy;

    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account doctor;

    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    @ManyToOne
    private Account modifiedBy;

    @Column(name = "version")
    private Long version;

    public DocumentationEntry() {
    }

    public DocumentationEntry(Long id) {
        this.id = id;
    }

    public DocumentationEntry(Long id, Long creationDateTime) {
        this.id = id;
        this.creationDateTime = creationDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWasDone() {
        return wasDone;
    }

    public void setWasDone(String wasDone) {
        this.wasDone = wasDone;
    }

    public String getToBeDone() {
        return toBeDone;
    }

    public void setToBeDone(String toBeDone) {
        this.toBeDone = toBeDone;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Long creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Long getModificationDateTime() {
        return modificationDateTime;
    }

    public void setModificationDateTime(Long modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
    }

    public Account getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
    }

    public Account getDoctor() {
        return doctor;
    }

    public void setDoctor(Account doctor) {
        this.doctor = doctor;
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
        if (!(object instanceof DocumentationEntry)) {
            return false;
        }
        DocumentationEntry other = (DocumentationEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.DocumentationEntry[ id=" + id + " ]";
    }
    
}
