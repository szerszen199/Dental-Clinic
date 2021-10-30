package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * Typ Medical documentation - klasa encyjna dla dokumentacji medycznej.
 */
@Entity
@Table(name = "medical_documentations")
@NamedQueries({
        @NamedQuery(name = "MedicalDocumentation.findAll", query = "SELECT m FROM MedicalDocumentation m"),
        @NamedQuery(name = "MedicalDocumentation.findById", query = "SELECT m FROM MedicalDocumentation m WHERE m.id = :id"),
        @NamedQuery(name = "MedicalDocumentation.findByAllergies", query = "SELECT m FROM MedicalDocumentation m WHERE m.allergies = :allergies"),
        @NamedQuery(name = "MedicalDocumentation.findByMedicationsTaken", query = "SELECT m FROM MedicalDocumentation m WHERE m.medicationsTaken = :medicationsTaken"),
        @NamedQuery(name = "MedicalDocumentation.findByVersion", query = "SELECT m FROM MedicalDocumentation m WHERE m.version = :version"),
        @NamedQuery(name = "MedicalDocumentation.findByPatientLogin", query = "SELECT a FROM MedicalDocumentation a WHERE a.patient.login = :accountLogin"),
        @NamedQuery(name = "MedicalDocumentation.findByCreationDateTime", query = "SELECT m FROM MedicalDocumentation m WHERE m.creationDateTime = :creationDateTime"),
        @NamedQuery(name = "MedicalDocumentation.findByModificationDateTime", query = "SELECT m FROM MedicalDocumentation m WHERE m.modificationDateTime = :modificationDateTime")})
public class MedicalDocumentation extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", updatable = false, nullable = false)
    //@NotNull
    private Long id;
    @Column(name = "allergies", columnDefinition = "text")
    private String allergies;
    @Column(name = "medications_taken", columnDefinition = "text")
    private String medicationsTaken;
    @JoinColumn(name = "patient_id", unique = true, referencedColumnName = "id", nullable = false, updatable = false)
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @NotNull
    private Account patient;

    /**
     * Tworzy nową instancję klasy MedicalDocumentation.
     */
    public MedicalDocumentation() {
    }

    /**
     * Tworzy nową instancję klasy MedicalDocumentation dla pacjenta.
     *
     * @param patient patient
     */
    public MedicalDocumentation(Account patient) {
        this.patient = patient;
    }

    @Override
    public Long getId() {
        return id;
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

    public Account getPatient() {
        return patient;
    }

    public void setPatient(Account patient) {
        this.patient = patient;
    }


    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.MedicalDocumentation[ id=" + id + " ]";
    }

}
