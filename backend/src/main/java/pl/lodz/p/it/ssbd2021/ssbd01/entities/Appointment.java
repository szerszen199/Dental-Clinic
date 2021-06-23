package pl.lodz.p.it.ssbd2021.ssbd01.entities;

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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Typ Appointment - klasa encyjna dla wizyt.
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
        @NamedQuery(name = "Appointment.findAllScheduled", query = "SELECT a FROM Appointment a WHERE a.patient != null AND a.doctor != null"),
        @NamedQuery(name = "Appointment.findAllScheduledByDoctor", query = "SELECT a FROM Appointment a WHERE a.patient != null AND a.doctor = :doctor"),
        @NamedQuery(name = "Appointment.findAllScheduledByPatient", query = "SELECT a FROM Appointment a WHERE a.patient = :patient AND a.doctor != null"),
        @NamedQuery(name = "Appointment.findByModificationDateTime", query = "SELECT a FROM Appointment a WHERE a.modificationDateTime = :modificationDateTime")})
public class Appointment extends AbstractEntity implements Serializable {

    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointments_generator")
    @SequenceGenerator(name = "appointments_generator", sequenceName = "appointments_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", updatable = false, nullable = false)
    @NotNull
    private Long id;

    @Basic(optional = false)
    @Column(name = "appointment_date", nullable = false)
    @Future
    @NotNull
    private LocalDateTime appointmentDate;

    @Basic(optional = true)
    @Column(name = "confirmation_date_time", nullable = true)
    private LocalDateTime confirmationDateTime;

    @Basic(optional = true)
    @Column(name = "cancellation_date_time", nullable = true)
    private LocalDateTime cancellationDateTime;

    @JoinColumn(name = "confirmed_by", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true)
    private Account confirmedBy;

    @JoinColumn(name = "canceled_by", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true)
    private Account canceledBy;


    @Basic(optional = false)
    @NotNull
    @Column(name = "reminder_mail_sent", nullable = false)
    private Boolean reminderMailSent;

    public LocalDateTime getConfirmationDateTime() {
        return confirmationDateTime;
    }

    public void setConfirmationDateTime(LocalDateTime confirmationDateTime) {
        this.confirmationDateTime = confirmationDateTime;
    }

    public LocalDateTime getCancellationDateTime() {
        return cancellationDateTime;
    }

    public void setCancellationDateTime(LocalDateTime cancellationDateTime) {
        this.cancellationDateTime = cancellationDateTime;
    }

    public Account getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(Account confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public Account getCanceledBy() {
        return canceledBy;
    }

    public void setCanceledBy(Account canceledBy) {
        this.canceledBy = canceledBy;
    }

    @Basic(optional = false)
    @Column(name = "confirmed", nullable = false)
    @NotNull
    private Boolean confirmed = false;

    @Basic(optional = false)
    @Column(name = "canceled", nullable = false)
    @NotNull
    private Boolean canceled = false;

    @Column(name = "rating", columnDefinition = "numeric", precision = 2, scale = 1)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    @Digits(integer = 1, fraction = 1)
    private Double rating;

    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Account doctor;

    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne
    private Account patient;

    /**
     * Tworzy nową instancję Appointment.
     */
    public Appointment() {
    }

    /**
     * Tworzy nowa instancje  Appointment.
     *
     * @param doctor          doktor
     * @param appointmentDate data wizyty
     */
    public Appointment(Account doctor, LocalDateTime appointmentDate) {
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
    }

    /**
     * Tworzy nowa instancje  Appointment.
     *
     * @param appointmentDate data wizyty
     * @param confirmed       status wizyty (potwierdzone)
     * @param canceled        status wizyty (odwolane)
     */
    public Appointment(LocalDateTime appointmentDate, Boolean confirmed, Boolean canceled) {
        this.appointmentDate = appointmentDate;
        this.confirmed = confirmed;
        this.canceled = canceled;
    }

    @Override
    public Long getId() {
        return id;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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

    public Boolean getReminderMailSent() {
        return reminderMailSent;
    }

    public void setReminderMailSent(Boolean reminderMailSent) {
        this.reminderMailSent = reminderMailSent;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment[ id=" + id + " ]";
    }

}
