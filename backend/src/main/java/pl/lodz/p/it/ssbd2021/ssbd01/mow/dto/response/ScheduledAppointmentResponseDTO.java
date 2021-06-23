package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignableEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Typ ScheduledAppointmentResponseDTO.
 */
public class ScheduledAppointmentResponseDTO implements SignableEntity {

    private final Long id;
    private final String doctorLogin;
    private final String patientLogin;
    private final LocalDateTime date;
    private final Long version;
    private final String doctorFirstName;
    private final String doctorLastName;
    private final String patientFirstName;
    private final String patientLastName;
    private final String etag;
    private final boolean canceled;
    private final boolean confirmed;

    /**
     * DTO do zwracania umówionej wizyty.
     *
     * @param appointment                  wizyta, na podstawie której tworzone jest DTO.
     * @param entityIdentitySignerVerifier obiekt klasy do funkcjonalności podpisywania etag
     */
    public ScheduledAppointmentResponseDTO(Appointment appointment, EntityIdentitySignerVerifier entityIdentitySignerVerifier) {
        this.id = appointment.getId();
        this.doctorLogin = appointment.getDoctor().getLogin();
        this.patientLogin = appointment.getPatient().getLogin();
        this.date = appointment.getAppointmentDate();
        this.version = appointment.getVersion();
        this.patientFirstName = appointment.getPatient().getFirstName();
        this.patientLastName = appointment.getPatient().getLastName();
        this.doctorFirstName = appointment.getDoctor().getFirstName();
        this.doctorLastName = appointment.getDoctor().getLastName();
        this.canceled = appointment.getCanceled();
        this.confirmed = appointment.getConfirmed();
        this.etag = entityIdentitySignerVerifier.sign(this);
    }

    public boolean isCanceled() {
        return canceled;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getEtag() {
        return etag;
    }

    public Long getId() {
        return id;
    }


    public String getDoctorLogin() {
        return doctorLogin;
    }


    public String getPatientLogin() {
        return patientLogin;
    }


    public LocalDateTime getDate() {
        return date;
    }


    public Long getVersion() {
        return version;
    }


    public String getDoctorFirstName() {
        return doctorFirstName;
    }


    public String getDoctorLastName() {
        return doctorLastName;
    }


    public String getPatientFirstName() {
        return patientFirstName;
    }


    public String getPatientLastName() {
        return patientLastName;
    }

    @Override
    public Map<String, String> getPayload() {
        Map<String, String> map = new HashMap<>();
        map.put("version", getVersion().toString());
        return map;
    }
}
