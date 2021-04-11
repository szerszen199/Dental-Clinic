package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
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

/**
 * Typ Prescription.
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
public class Prescription extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prescriptions_generator")
    @SequenceGenerator(name = "prescriptions_generator", sequenceName = "prescriptions_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "medications")
    private String medications;

    @Basic(optional = false)
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "modification_date_time")
    private LocalDateTime modificationDateTime;

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

    /**
     * Tworzy nową instancję klasy Prescription.
     */
    public Prescription() {
    }

    /**
     * Tworzy nową instancję klasy Prescription.
     *
     * @param medications      przepisane leki
     * @param creationDateTime data utworzenia
     */
    public Prescription(String medications, LocalDateTime creationDateTime) {
        this.medications = medications;
        this.creationDateTime = creationDateTime;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
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

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription[ id=" + id + " ]";
    }
    
}
