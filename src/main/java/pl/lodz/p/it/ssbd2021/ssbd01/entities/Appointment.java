/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "appointments")
@NamedQueries({
    @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a"),
    @NamedQuery(name = "Appointment.findById", query = "SELECT a FROM Appointment a WHERE a.id = :id"),
    @NamedQuery(name = "Appointment.findByAppointmentDate", query = "SELECT a FROM Appointment a WHERE a.appointmentDate = :appointmentDate"),
    @NamedQuery(name = "Appointment.findByConfirmed", query = "SELECT a FROM Appointment a WHERE a.confirmed = :confirmed"),
    @NamedQuery(name = "Appointment.findByCanceled", query = "SELECT a FROM Appointment a WHERE a.canceled = :canceled"),
    @NamedQuery(name = "Appointment.findByRating", query = "SELECT a FROM Appointment a WHERE a.rating = :rating"),
    @NamedQuery(name = "Appointment.findByVersion", query = "SELECT a FROM Appointment a WHERE a.version = :version"),
    @NamedQuery(name = "Appointment.findByCreationDateTime", query = "SELECT a FROM Appointment a WHERE a.creationDateTime = :creationDateTime"),
    @NamedQuery(name = "Appointment.findByModificationDateTime", query = "SELECT a FROM Appointment a WHERE a.modificationDateTime = :modificationDateTime")})
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "appointment_date")
    private long appointmentDate;
    @Basic(optional = false)
    @Column(name = "confirmed")
    private int confirmed;
    @Basic(optional = false)
    @Column(name = "canceled")
    private int canceled;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private BigDecimal rating;
    @Column(name = "version")
    private BigInteger version;
    @Basic(optional = false)
    @Column(name = "creation_date_time")
    private long creationDateTime;
    @Column(name = "modification_date_time")
    private BigInteger modificationDateTime;
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account_1 doctorId;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne
    private Account_1 patientId;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account_1 createdBy;
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    @ManyToOne
    private Account_1 modifiedBy;

    public Appointment() {
    }

    public Appointment(Long id) {
        this.id = id;
    }

    public Appointment(Long id, long appointmentDate, int confirmed, int canceled, long creationDateTime) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.confirmed = confirmed;
        this.canceled = canceled;
        this.creationDateTime = creationDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(long appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getCanceled() {
        return canceled;
    }

    public void setCanceled(int canceled) {
        this.canceled = canceled;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
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

    public Account_1 getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Account_1 doctorId) {
        this.doctorId = doctorId;
    }

    public Account_1 getPatientId() {
        return patientId;
    }

    public void setPatientId(Account_1 patientId) {
        this.patientId = patientId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment[ id=" + id + " ]";
    }
    
}
