package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto;

public class BookAppointmentDto {

    private long appointmentId;

    public BookAppointmentDto(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public BookAppointmentDto() {
    }

}
