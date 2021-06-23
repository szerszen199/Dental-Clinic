package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignableEntity;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AppointmentSlotEditRequestDTO implements SignableEntity {

    @NotNull(message = I18n.DOCTOR_ID_NULL)
    @Login
    private String doctorLogin;

    @Future(message = I18n.APPOINTMENT_DATE_FUTURE)
    @NotNull(message = I18n.APPOINTMENT_DATE_NULL)
    private LocalDateTime appointmentDate;

    @NotNull(message = I18n.VERSION_NULL)
    private Long version;

    @NotNull(message = I18n.APPOINTMENT_ID_NULL)
    private Long id;

    public String getDoctorLogin() {
        return doctorLogin;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public Long getVersion() {
        return version;
    }

    public Long getId() {
        return id;
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
    @JsonbTransient
    public Map<String, String> getPayload() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("version", String.valueOf(version));
        map.put("id", getId().toString());
        return map;
    }
}
