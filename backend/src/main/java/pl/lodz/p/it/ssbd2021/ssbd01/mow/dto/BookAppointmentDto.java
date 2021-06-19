package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;

import java.time.LocalDateTime;

public class BookAppointmentDto {

    private LocalDateTime appointmentDate;
    private Account createdBy;
    private Account doctor;
    private Account patient;

    /**
     * Instantiates a new Book appointment dto.
     *
     * @param appointmentDate the appointment date
     * @param createdBy       the created by
     * @param doctor          the doctor
     * @param patient         the patient
     */
    public BookAppointmentDto(LocalDateTime appointmentDate, Account createdBy, Account doctor, Account patient) {
        this.appointmentDate = appointmentDate;
        this.createdBy = createdBy;
        this.doctor = doctor;
        this.patient = patient;
    }

    public BookAppointmentDto(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
        this.createdBy = createdBy;
        this.doctor = doctor;
        this.patient = patient;
    }

    /**
     * Instantiates a new Book appointment dto.
     */
    public BookAppointmentDto() {
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Account getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
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
}
