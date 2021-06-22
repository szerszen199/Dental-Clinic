package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignableEntity;

import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Typ Appointment info response dto.
 */
public class AppointmentInfoResponseDTO implements SignableEntity {

    @NotNull(message = I18n.APPOINTMENT_ID_NULL)
    private Long id;

    @Future
    @NotNull(message = I18n.APPOINTMENT_DATE_NULL)
    private LocalDateTime appointmentDate;

    @NotNull(message = I18n.APPOINTMENT_CONFIRMED_NULL)
    private Boolean confirmed;

    @NotNull(message = I18n.APPOINTMENT_CANCELED_NULL)
    private Boolean canceled;

    @NotNull(message = I18n.DOCTOR_LOGIN_NULL)
    private String doctorLogin;

    @NotNull
    private Long version;

    private String patientLogin;

    private LocalDateTime confirmationDateTime;

    private LocalDateTime cancellationDateTime;

    /**
     * Tworzy nową instancję klasy AppointmentInfoResponseDTO.
     *
     * @param appointment wizyta
     */
    public AppointmentInfoResponseDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.appointmentDate = appointment.getAppointmentDate();
        this.confirmed = appointment.getConfirmed();
        this.canceled = appointment.getCanceled();
        this.doctorLogin = appointment.getDoctor().getLogin();
        this.version = appointment.getVersion();
        if (appointment.getPatient() != null) {
            this.patientLogin = appointment.getPatient().getLogin();
        }
        this.confirmationDateTime = appointment.getConfirmationDateTime();
        this.cancellationDateTime = appointment.getCancellationDateTime();
    }

    /**
     * Tworzy nową instancję klasy AppointmentInfoResponseDTO.
     */
    public AppointmentInfoResponseDTO() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }

    public void setDoctorLogin(String doctorLogin) {
        this.doctorLogin = doctorLogin;
    }

    public void setPatientLogin(String patientLogin) {
        this.patientLogin = patientLogin;
    }

    public void setConfirmationDateTime(LocalDateTime confirmationDateTime) {
        this.confirmationDateTime = confirmationDateTime;
    }

    public void setCancellationDateTime(LocalDateTime cancellationDateTime) {
        this.cancellationDateTime = cancellationDateTime;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public String getDoctorLogin() {
        return doctorLogin;
    }

    public String getPatientLogin() {
        return patientLogin;
    }

    public LocalDateTime getConfirmationDateTime() {
        return confirmationDateTime;
    }

    public LocalDateTime getCancellationDateTime() {
        return cancellationDateTime;
    }

    @Override
    @JsonbTransient
    public Map<String, String> getPayload() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("version", getVersion().toString());
        map.put("id", getId().toString());
        return map;
    }
}
