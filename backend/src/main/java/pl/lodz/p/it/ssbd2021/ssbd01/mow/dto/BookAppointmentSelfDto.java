package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;

public class BookAppointmentSelfDto {

    @NotNull(message = I18n.APPOINTMENT_ID_NULL)
    private Long appointmentId;
    @NotNull(message = I18n.APPOINTMENT_ID_NULL)
    private Long patientId;

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public BookAppointmentSelfDto(Long version, Long appointmentId, Long patientId) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
    }

    public BookAppointmentSelfDto() {
    }

}
