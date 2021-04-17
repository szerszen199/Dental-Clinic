package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
        @NamedQuery(name = "DocumentationEntry.findByModificationDateTime", query = "SELECT d FROM DocumentationEntry d WHERE d.modificationDateTime = :modificationDateTime")})
public class DocumentationEntry extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documentation_entries_generator")
    @SequenceGenerator(name = "documentation_entries_generator", sequenceName = "documentation_entries_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "was_done")
    private String wasDone;

    @Column(name = "to_be_done")
    private String toBeDone;

    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private Account doctor;

    /**
     * Tworzy nową instancję klasy DocumentationEntry.
     */
    public DocumentationEntry() {
    }

    @Override
    public Long getId() {
        return id;
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

    public Account getDoctor() {
        return doctor;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.DocumentationEntry[ id=" + id + " ]";
    }

}
