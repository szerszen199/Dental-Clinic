package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Typ Documentation entry reprezentujący wpisy do dokumentacji.
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
        @NamedQuery(name = "DocumentationEntry.deleteById", query = "DELETE FROM DocumentationEntry d WHERE d.id = :id"),
        @NamedQuery(name = "DocumentationEntry.findByPatientLogin",
                query = "SELECT d FROM DocumentationEntry d, MedicalDocumentation  md WHERE md.id = d.medicalDocumentation.id and md.patient.login = :login"),
        @NamedQuery(name = "DocumentationEntry.findByModificationDateTime", query = "SELECT d FROM DocumentationEntry d WHERE d.modificationDateTime = :modificationDateTime")})
public class DocumentationEntry extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documentation_entries_generator")
    @SequenceGenerator(name = "documentation_entries_generator", sequenceName = "documentation_entries_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", updatable = false, nullable = false)
    @NotNull
    private Long id;

    @Column(name = "was_done")
    private byte[] wasDone;

    @Column(name = "to_be_done")
    private byte[] toBeDone;

    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    @NotNull
    private Account doctor;

    @JoinColumn(name = "documentation_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = true)
    @ManyToOne(optional = false)
    @NotNull
    private MedicalDocumentation medicalDocumentation;

    /**
     * Tworzy nową instancję klasy DocumentationEntry.
     */
    public DocumentationEntry() {
    }

    /**
     * Tworzy nową instancję klasy Documentation entry.
     *
     * @param doctor               doktor tworzący wpis dokumentacji
     * @param wasDone              co zostało zrobione
     * @param toBeDone             co ma zostać zrobione wprzyszłości
     * @param medicalDocumentation medyczna dokumentacja, dla której został dodany wpis
     */
    public DocumentationEntry(Account doctor, byte[] wasDone, byte[] toBeDone, MedicalDocumentation medicalDocumentation) {
        this.doctor = doctor;
        this.wasDone = wasDone;
        this.toBeDone = toBeDone;
        this.medicalDocumentation = medicalDocumentation;
    }

    public MedicalDocumentation getMedicalDocumentation() {
        return medicalDocumentation;
    }

    public void setMedicalDocumentation(MedicalDocumentation medicalDocumentation) {
        this.medicalDocumentation = medicalDocumentation;
    }

    @Override
    public Long getId() {
        return id;
    }

    public byte[] getWasDone() {
        return wasDone;
    }

    public void setWasDone(byte[] wasDone) {
        this.wasDone = wasDone;
    }

    public byte[] getToBeDone() {
        return toBeDone;
    }

    public void setToBeDone(byte[] toBeDone) {
        this.toBeDone = toBeDone;
    }

    public Account getDoctor() {
        return doctor;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.DocumentationEntry[ id=" + id + " ]";
    }

}
