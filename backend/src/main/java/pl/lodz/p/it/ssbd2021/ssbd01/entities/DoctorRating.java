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
                query = "SELECT d FROM DoctorRating d INNER JOIN AccessLevel al ON d.doctor.id = al.accountId.id WHERE al.active = true AND al.level = 'level.doctor'"),
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

    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false, updatable = false)
    @OneToOne(optional = false)
    @NotNull
    private Account doctor;

    @Column(name = "rates_sum", columnDefinition = "double precission", nullable = false)
    @DecimalMin(value = "0.0")
    private Double ratesSum = 0d;

    @Column(name = "rates_counter", nullable = false)
    @Min(0)
    private Integer ratesCounter = 0;

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

    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * Oblicza średnią ocen dla doktora.
     *
     * @return średnia ocen
     */
    public double getAverage() {
        if (ratesCounter == 0) {
            return 0d;
        }
        return ratesSum / ratesCounter;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorRating[ id=" + id + " ]";
    }
}
