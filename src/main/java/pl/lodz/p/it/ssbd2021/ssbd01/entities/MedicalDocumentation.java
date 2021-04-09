/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author student
 */
@Entity
@Table(name = "medical_documentations")
@NamedQueries({
    @NamedQuery(name = "MedicalDocumentation.findAll", query = "SELECT m FROM MedicalDocumentation m"),
    @NamedQuery(name = "MedicalDocumentation.findById", query = "SELECT m FROM MedicalDocumentation m WHERE m.id = :id"),
    @NamedQuery(name = "MedicalDocumentation.findByAllergies", query = "SELECT m FROM MedicalDocumentation m WHERE m.allergies = :allergies"),
    @NamedQuery(name = "MedicalDocumentation.findByMedicationsTaken", query = "SELECT m FROM MedicalDocumentation m WHERE m.medicationsTaken = :medicationsTaken"),
    @NamedQuery(name = "MedicalDocumentation.findByVersion", query = "SELECT m FROM MedicalDocumentation m WHERE m.version = :version"),
    @NamedQuery(name = "MedicalDocumentation.findByCreationDateTime", query = "SELECT m FROM MedicalDocumentation m WHERE m.creationDateTime = :creationDateTime"),
    @NamedQuery(name = "MedicalDocumentation.findByModificationDateTime", query = "SELECT m FROM MedicalDocumentation m WHERE m.modificationDateTime = :modificationDateTime")})
public class MedicalDocumentation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "allergies")
    private String allergies;
    @Column(name = "medications_taken")
    private String medicationsTaken;
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
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    @ManyToOne
    private Account_1 modifiedBy;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account_1 patientId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentationId")
    private Collection<DocumentationEntry> documentationEntryCollection;

    public MedicalDocumentation() {
    }

    public MedicalDocumentation(Long id) {
        this.id = id;
    }

    public MedicalDocumentation(Long id, long creationDateTime) {
        this.id = id;
        this.creationDateTime = creationDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getMedicationsTaken() {
        return medicationsTaken;
    }

    public void setMedicationsTaken(String medicationsTaken) {
        this.medicationsTaken = medicationsTaken;
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

    public Account_1 getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Account_1 modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Account_1 getPatientId() {
        return patientId;
    }

    public void setPatientId(Account_1 patientId) {
        this.patientId = patientId;
    }

    public Collection<DocumentationEntry> getDocumentationEntryCollection() {
        return documentationEntryCollection;
    }

    public void setDocumentationEntryCollection(Collection<DocumentationEntry> documentationEntryCollection) {
        this.documentationEntryCollection = documentationEntryCollection;
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
        if (!(object instanceof MedicalDocumentation)) {
            return false;
        }
        MedicalDocumentation other = (MedicalDocumentation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.MedicalDocumentation[ id=" + id + " ]";
    }
    
}
