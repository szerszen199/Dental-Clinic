package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.validation.constraints.NotNull;

public class BookAppointmentDto {

    @NotNull(message = I18n.APPOINTMENT_ID_NULL)
    private Long appointmentId;
    @NotNull(message = I18n.PATIENT_LOGIN_NULL)
    private String patientLogin;

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientLogin() {
        return patientLogin;
    }

    public void setPatientLogin(String patientLogin) {
        this.patientLogin = patientLogin;
    }

    public BookAppointmentDto() {
    }

}
