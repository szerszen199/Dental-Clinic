package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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

    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointments_generator")
    @SequenceGenerator(name = "appointments_generator", sequenceName = "appointments_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;
    @Basic(optional = false)
    @Column(name = "confirmed")
    private Boolean confirmed;
    @Basic(optional = false)
    @Column(name = "canceled")
    private Boolean canceled;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private BigDecimal rating;
    @Column(name = "version")
    private Long version;
    @Basic(optional = false)
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;
    @Column(name = "modification_date_time")
    private LocalDateTime modificationDateTime;
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account doctor;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne
    private Account patient;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account createdBy;
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    @ManyToOne
    private Account modifiedBy;

    /**
     * Tworzy nową instancję Appointment.
     */
    public Appointment() {
    }

    /**
     * Tworzy nową instancję  Appointment.
     *
     * @param id klucz glowny
     */
    public Appointment(Long id) {
        this.id = id;
    }

    /**
     * Tworzy nowa instancje  Appointment.
     *
     * @param id               klucz glowny
     * @param appointmentDate  data wizyty
     * @param confirmed        status wizyty (potwierdzone)
     * @param canceled         status wizyty (odwolane)
     * @param creationDateTime data utworzenia
     */
    public Appointment(Long id, LocalDateTime appointmentDate, Boolean confirmed, Boolean canceled, LocalDateTime creationDateTime) {
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

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
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

    public Account getDoctor() {
        return doctor;
    }

    public void setDoctor(Account doctor) {
        this.doctor = doctor;
    }

    public Account getPatient() {
        return patient;
    }

    public void setPatient(Account patient) {
        this.patient = patient;
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
        // TODO: Warning - this method won't work in the case klucz glowny fields are not set
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
