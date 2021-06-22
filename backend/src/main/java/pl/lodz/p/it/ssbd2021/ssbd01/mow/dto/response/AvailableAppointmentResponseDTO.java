package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AvailableAppointmentResponseDTO {

    @NotNull
    Long id;
    @NotNull
    LocalDateTime date;
    @NotNull
    Long version;
    @NotNull
    DoctorInfoResponseDTO doctor;

    /**
     * DTO do zwracania terminów wizyt.
     *
     * @param appointment termin wizyty na podstawie którego tworzone jest DTO.
     */
    public AvailableAppointmentResponseDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.date = appointment.getAppointmentDate();
        this.version = appointment.getVersion();
        this.doctor = new DoctorInfoResponseDTO(appointment.getDoctor());

    }

    public DoctorInfoResponseDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorInfoResponseDTO doctor) {
        this.doctor = doctor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
