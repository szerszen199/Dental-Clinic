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
@Table(name = "prescriptions")
@NamedQueries({
    @NamedQuery(name = "Prescription.findAll", query = "SELECT p FROM Prescription p"),
    @NamedQuery(name = "Prescription.findById", query = "SELECT p FROM Prescription p WHERE p.id = :id"),
    @NamedQuery(name = "Prescription.findByMedications", query = "SELECT p FROM Prescription p WHERE p.medications = :medications"),
    @NamedQuery(name = "Prescription.findByVersion", query = "SELECT p FROM Prescription p WHERE p.version = :version"),
    @NamedQuery(name = "Prescription.findByCreationDateTime", query = "SELECT p FROM Prescription p WHERE p.creationDateTime = :creationDateTime"),
    @NamedQuery(name = "Prescription.findByModificationDateTime", query = "SELECT p FROM Prescription p WHERE p.modificationDateTime = :modificationDateTime")})
public class Prescription implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "medications")
    private String medications;
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
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account_1 patientId;

    public Prescription() {
    }

    public Prescription(Long id) {
        this.id = id;
    }

    public Prescription(Long id, String medications, long creationDateTime) {
        this.id = id;
        this.medications = medications;
        this.creationDateTime = creationDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
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

    public Account_1 getPatientId() {
        return patientId;
    }

    public void setPatientId(Account_1 patientId) {
        this.patientId = patientId;
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
        if (!(object instanceof Prescription)) {
            return false;
        }
        Prescription other = (Prescription) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription[ id=" + id + " ]";
    }
    
}
