package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response;

import java.util.List;

/**
 * Typ DTO dla wszystkich umówionych wizyt.
 */
public class AllScheduledAppointmentsResponseDTO {

    private List<ScheduledAppointmentResponseDTO> scheduledAppointmentResponseDTOS;

    /**
     * Tworzy nową instancję klasy AllScheduledAppointmentsResponseDTO.
     *
     * @param scheduledAppointmentResponseDTOS AllScheduledAppointmentsResponseDTO
     */
    public AllScheduledAppointmentsResponseDTO(List<ScheduledAppointmentResponseDTO> scheduledAppointmentResponseDTOS) {
        this.scheduledAppointmentResponseDTOS = scheduledAppointmentResponseDTOS;
    }

    /**
     * Tworzy nową instancję klasy AllScheduledAppointmentsResponseDTO.
     */
    public AllScheduledAppointmentsResponseDTO() {
    }

    public List<ScheduledAppointmentResponseDTO> getScheduledAppointmentResponseDTOS() {
        return scheduledAppointmentResponseDTOS;
    }

    public void setScheduledAppointmentResponseDTOS(List<ScheduledAppointmentResponseDTO> scheduledAppointmentResponseDTOS) {
        this.scheduledAppointmentResponseDTOS = scheduledAppointmentResponseDTOS;
    }
}
