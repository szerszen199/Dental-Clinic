package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AvailableAppointmentResponseDTO {

    @NotNull
    Long id;
    @NotNull
    Long doctorId;
    @NotNull
    LocalDateTime date;
    @NotNull
    Long version;


    public AvailableAppointmentResponseDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.doctorId = appointment.getDoctor().getId();
        this.date = appointment.getAppointmentDate();
        this.version = appointment.getVersion();

    }


}
