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

    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account patient;

    @Column(name = "version")
    private Long version;

    public Prescription() {
    }

    public Prescription(Long id) {
        this.id = id;
    }

    public Prescription(Long id, String medications, Long creationDateTime) {
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

    public Account getPatient() {
        return patient;
    }

    public void setPatient(Account patient) {
        this.patient = patient;
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
