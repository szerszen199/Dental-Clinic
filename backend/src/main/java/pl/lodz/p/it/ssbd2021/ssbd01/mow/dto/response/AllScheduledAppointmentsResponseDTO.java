package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response;

import java.util.List;

public class AllScheduledAppointmentsResponseDTO {

    private List<ScheduledAppointmentResponseDTO> scheduledAppointmentResponseDTOS;

    public AllScheduledAppointmentsResponseDTO(List<ScheduledAppointmentResponseDTO> scheduledAppointmentResponseDTOS) {
        this.scheduledAppointmentResponseDTOS = scheduledAppointmentResponseDTOS;
    }

    public AllScheduledAppointmentsResponseDTO() {
    }

    public List<ScheduledAppointmentResponseDTO> getScheduledAppointmentResponseDTOS() {
        return scheduledAppointmentResponseDTOS;
    }

    public void setScheduledAppointmentResponseDTOS(List<ScheduledAppointmentResponseDTO> scheduledAppointmentResponseDTOS) {
        this.scheduledAppointmentResponseDTOS = scheduledAppointmentResponseDTOS;
    }
}
