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
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "medications", nullable = false)
    private String medications;

    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private Account doctor;

    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private Account patient;

    /**
     * Tworzy nową instancję klasy Prescription.
     */
    public Prescription() {
    }

    /**
     * Tworzy nową instancję klasy Prescription.
     *
     * @param medications przepisane leki
     */
    public Prescription(String medications) {
        this.medications = medications;
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

    public Account getPatient() {
        return patient;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription[ id=" + id + " ]";
    }

}
