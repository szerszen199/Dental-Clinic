package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request;

import javax.validation.constraints.NotNull;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

/**
 * Typ RemoveAppointmentSlotRequestDTO - do funkcjonalności usuwania dostępnego terminu wizyty.
 */
public class RemoveAppointmentSlotRequestDTO {

    @NotNull(message = I18n.APPOINTMENT_ID_NULL)
    private Long id;

    /**
     * Tworzy nową instancję klasy RemoveAppointmentSlotRequestDTO.
     */
    public RemoveAppointmentSlotRequestDTO() {
    }

    /**
     * Tworzy nową instancję klasy RemoveAppointmentSlotRequestDTO.
     *
     * @param id klucz główny niezarezerwowanej wizyty
     */
    public RemoveAppointmentSlotRequestDTO(@NotNull(message = I18n.APPOINTMENT_ID_NULL) Long id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return klucz główny niezarezerwowanej wizyty
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id klucz główny niezarezerwowanej wizyty
     */
    public void setId(Long id) {
        this.id = id;
    }
}
