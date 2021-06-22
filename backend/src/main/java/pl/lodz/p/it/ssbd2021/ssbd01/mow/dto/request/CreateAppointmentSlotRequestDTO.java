package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request;

import org.hibernate.validator.constraints.pl.PESEL;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.validation.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Klasa Dto dla tworzenia slotu wizyty.
 */
public class CreateAppointmentSlotRequestDTO {

    @NotNull(message = I18n.DOCTOR_ID_NULL)
    private String doctorLogin;

    @Future(message = I18n.APPOINTMENT_DATE_FUTURE)
    @NotNull(message = I18n.APPOINTMENT_DATE_NULL)
    private LocalDateTime appointmentDate;

    /**
     * Tworzy nową instancję klasy CreateAppointmentSlotRequestDTO.
     *
     * @param doctorLogin     login doktora
     * @param appointmentDate data i godzina wizyty
     */
    public CreateAppointmentSlotRequestDTO(String doctorLogin, LocalDateTime appointmentDate) {
        this.doctorLogin = doctorLogin;
        this.appointmentDate = appointmentDate;
    }

    /**
     * Tworzy nową instancję klasy CreateAppointmentSlotRequestDTO.
     */
    public CreateAppointmentSlotRequestDTO() {
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDoctorLogin() {
        return doctorLogin;
    }

    public void setDoctorLogin(String doctorLogin) {
        this.doctorLogin = doctorLogin;
    }
}
