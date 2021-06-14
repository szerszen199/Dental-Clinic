package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

/**
 * Typ DoctorRating - klasa reprezentująca encję ocen doktora.
 */
@Entity
@Table(name = "doctors_ratings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"doctor_id"})})
@NamedQueries({
        @NamedQuery(name = "DoctorRating.findAll", query = "SELECT d FROM DoctorRating d"),
        @NamedQuery(name = "DoctorRating.findById", query = "SELECT d FROM DoctorRating d WHERE d.id = :id"),
        @NamedQuery(name = "DoctorRating.findByDoctorId", query = "SELECT d FROM DoctorRating d WHERE d.doctor.id = :doctorId"),
        @NamedQuery(name = "DoctorRating.findByDoctorLogin", query = "SELECT d FROM DoctorRating d WHERE d.doctor.login = :doctorLogin"),
        @NamedQuery(name = "DoctorRating.findActiveDoctors", 
                query = "SELECT d FROM DoctorRating d WHERE d.doctor.id = (SELECT accountId.id FROM AccessLevel al WHERE al.active AND al.level = 'level.doctor')"),
        @NamedQuery(name = "DoctorRating.findByActive", query = "SELECT d FROM DoctorRating d WHERE d.active = :active"),
        @NamedQuery(name = "DoctorRating.findByVersion", query = "SELECT d FROM DoctorRating d WHERE d.version = :version"),
        @NamedQuery(name = "DoctorRating.findByRatesSum", query = "SELECT d FROM DoctorRating d WHERE d.ratesSum = :ratesSum"),
        @NamedQuery(name = "DoctorRating.findByRatesCounter", query = "SELECT d FROM DoctorRating d WHERE d.ratesCounter = :ratesCounter")})
public class DoctorRating extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_ratings_generator")
    @SequenceGenerator(name = "doctor_ratings_generator", sequenceName = "doctors_ratings_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", updatable = false, nullable = false)
    @NotNull
    private Long id;

    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false, updatable = false)
    @OneToOne(optional = false)
    @Column(name = "doctor_id", updatable = false, nullable = false)
    @NotNull
    private Account doctor;

    @Column(name = "rates_sum", columnDefinition = "numeric", precision = 2, scale = 1, nullable = false)
    @DecimalMin(value = "0.0")
    private Double ratesSum = 0d;

    @Column(name = "rates_counter", nullable = false)
    @Min(0)
    private Integer ratesCounter = 0;

    @Basic(optional = false)
    @Column(name = "active", nullable = false)
    @NotNull
    private Boolean active;

    /**
     * Tworzy nową instancję klasy DoctorRating.
     */
    public DoctorRating() {
    }

    /**
     * Tworzy nową instancję klasy DoctorRating.
     *
     * @param doctor lekarz, którego ocena dotyczy
     */
    public DoctorRating(@NotNull Account doctor) {
        this.doctor = doctor;
        this.active = doctor.getActive() 
                && doctor.getAccessLevels().stream().anyMatch(al -> al.getLevel().equals(I18n.DOCTOR) && al.getActive());
    }

    /**
     * Pobiera pole doctor.
     *
     * @return lekarz doctor
     */
    public Account getDoctor() {
        return doctor;
    }

    /**
     * Pobiera pole ratesSum.
     *
     * @return suma wszystkich ocen
     */
    public Double getRatesSum() {
        return ratesSum;
    }

    /**
     * Ustawia pole ratesSum.
     *
     * @param ratesSum suma wszystkich ocen
     */
    public void setRatesSum(Double ratesSum) {
        this.ratesSum = ratesSum;
    }

    /**
     * Pobiera pole ratesCounter.
     *
     * @return liczba wszystkich ocen
     */
    public Integer getRatesCounter() {
        return ratesCounter;
    }

    /**
     * Ustawia pole ratesCounter.
     *
     * @param ratesCounter liczba wszystkich ocen
     */
    public void setRatesCounter(Integer ratesCounter) {
        this.ratesCounter = ratesCounter;
    }

    /**
     * Pobiera pole active.
     *
     * @return wartość active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Ustawia pole active.
     *
     * @param active wartość acrive
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public Long getId() {
        return null;
    }
    
    public double getAverage() {
        return ratesSum / ratesCounter;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorRating[ id=" + id + " ]";
    }
}
