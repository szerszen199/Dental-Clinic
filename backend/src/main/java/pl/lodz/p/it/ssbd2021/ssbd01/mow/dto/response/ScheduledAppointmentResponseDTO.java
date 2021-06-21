package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Typ ScheduledAppointmentResponseDTO.
 */
public class ScheduledAppointmentResponseDTO {

    @NotNull
    Long id;

    @NotNull
    @Login
    String doctorLogin;

    @NotNull
    @Login
    String patientLogin;

    @NotNull
    LocalDateTime date;

    @NotNull
    Long version;

    @NotNull
    String doctorFirstName;

    @NotNull
    String doctorLastName;

    @NotNull
    String patientFirstName;

    @NotNull
    String patientLastName;

    /**
     * DTO do zwracania umówionej wizyty.
     *
     * @param appointment wizyta, na podstawie której tworzone jest DTO.
     */
    public ScheduledAppointmentResponseDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.doctorLogin = appointment.getDoctor().getLogin();
        this.patientLogin = appointment.getPatient().getLogin();
        this.date = appointment.getAppointmentDate();
        this.version = appointment.getVersion();
        this.patientFirstName = appointment.getPatient().getFirstName();
        this.patientLastName = appointment.getPatient().getLastName();
        this.doctorFirstName = appointment.getDoctor().getLastName();
        this.doctorLastName = appointment.getDoctor().getLastName();
    }

    /**
     * Tworzy nową instancję klasy ScheduledAppointmentResponseDTO.
     */
    public ScheduledAppointmentResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorLogin() {
        return doctorLogin;
    }

    public void setDoctorLogin(String doctorLogin) {
        this.doctorLogin = doctorLogin;
    }

    public String getPatientLogin() {
        return patientLogin;
    }

    public void setPatientLogin(String patientLogin) {
        this.patientLogin = patientLogin;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }
}
