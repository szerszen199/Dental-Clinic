package pl.lodz.p.it.ssbd2021.ssbd01.utils.converters;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.CreateAppointmentSlotRequestDTO;

public class AppointmentConverter {


    /**
     * Tworzy nową instancję obiektu AppointmentEntity korzystając z danych z obiektu createAppointmentSlotRequestDTO (konwertuje obiekt).
     *
     * @param doctor obiekt typu Account definujący przypisanego doktora
     * @param createAppointmentSlotRequestDTO obiekt typu CreateAppointmentSlotRequestDTO
     * @return Appointment
     */
    public static Appointment createAppointmentEntityFromDto(Account doctor, CreateAppointmentSlotRequestDTO createAppointmentSlotRequestDTO) {
        return new Appointment(doctor, createAppointmentSlotRequestDTO.getAppointmentDate());
    }
}
