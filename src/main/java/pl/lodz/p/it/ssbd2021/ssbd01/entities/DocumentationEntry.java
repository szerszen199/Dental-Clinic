/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
    @Column(name = "version")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "creation_date_time")
    private long creationDateTime;
    @Column(name = "modification_date_time")
    private BigInteger modificationDateTime;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account_1 createdBy;
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account_1 doctorId;
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    @ManyToOne
    private Account_1 modifiedBy;
    @JoinColumn(name = "documentation_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MedicalDocumentation documentationId;

    public DocumentationEntry() {
    }

    public DocumentationEntry(Long id) {
        this.id = id;
    }

    public DocumentationEntry(Long id, long creationDateTime) {
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

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public long getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(long creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public BigInteger getModificationDateTime() {
        return modificationDateTime;
    }

    public void setModificationDateTime(BigInteger modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
    }

    public Account_1 getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Account_1 createdBy) {
        this.createdBy = createdBy;
    }

    public Account_1 getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Account_1 doctorId) {
        this.doctorId = doctorId;
    }

    public Account_1 getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Account_1 modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public MedicalDocumentation getDocumentationId() {
        return documentationId;
    }

    public void setDocumentationId(MedicalDocumentation documentationId) {
        this.documentationId = documentationId;
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
