package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;

public class BookAppointmentSelfDto {

    @NotNull(message = I18n.APPOINTMENT_ID_NULL)
    private Long appointmentId;

    /**
     * Tworzy instancję new Book appointment self dto.
     *
     * @param appointmentId the appointment id
     */
    public BookAppointmentSelfDto(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Tworzy instancję a new Book appointment self dto.
     */
    public BookAppointmentSelfDto() {
    }

}
