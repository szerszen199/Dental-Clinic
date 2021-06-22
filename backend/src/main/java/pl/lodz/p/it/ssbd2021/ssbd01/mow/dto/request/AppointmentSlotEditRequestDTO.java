package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AppointmentSlotEditRequestDTO implements SignableEntity {

    @NotNull(message = I18n.APPOINTMENT_ID_NULL)
    private Long id;

    @NotNull(message = I18n.DOCTOR_ID_NULL)
    @Login
    private String doctorLogin;

    @Future(message = I18n.APPOINTMENT_DATE_FUTURE)
    @NotNull(message = I18n.APPOINTMENT_DATE_NULL)
    private LocalDateTime appointmentDate;

    @NotNull(message = I18n.VERSION_NULL)
    private Long version;

    public Long getId() {
        return id;
    }

    public String getDoctorLogin() {
        return doctorLogin;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDoctorLogin(String doctorLogin) {
        this.doctorLogin = doctorLogin;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public Map<String, String> getPayload() {
        Map<String, String> map = new HashMap<>();
        map.put("version", String.valueOf(version));
        return map;
    }
}
