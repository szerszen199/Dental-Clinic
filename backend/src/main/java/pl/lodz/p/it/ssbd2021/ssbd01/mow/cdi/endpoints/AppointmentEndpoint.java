package pl.lodz.p.it.ssbd2021.ssbd01.mow.cdi.endpoints;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.AppointmentTransactionRepeater;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.RemoveAppointmentSlotRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers.AppointmentManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

/**
 * Typ AppointmentEndpoint - dla wizyt.
 */
@Path("appointment")
@Stateful
@DenyAll
@Interceptors(LogInterceptor.class)
public class AppointmentEndpoint {

    @Inject
    private AppointmentManager appointmentManager;

    @Inject
    private AppointmentTransactionRepeater appointmentTransactionRepeater;

    /**
     * Usuwa dostępny termin wizyty.
     *
     * @param removeAppointmentSlotRequestDTO obiekt DTO zawierający id usuwanego terminu wizyty
     * @return {@link Response.Status#OK} przy powodzeniu, inaczej {@link Response.Status#BAD_REQUEST}
     */
    @POST
    @Path("slot/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.RECEPTIONIST})
    public Response deleteAppointmentSlot(@NotNull @Valid RemoveAppointmentSlotRequestDTO removeAppointmentSlotRequestDTO) {
        try {
            appointmentTransactionRepeater.repeatTransaction(
                    () -> appointmentManager.deleteAppointmentSlot(removeAppointmentSlotRequestDTO.getId()),
                    appointmentManager);
        } catch (AppBaseException e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.APPOINTMENT_SLOT_REMOVED_UNSUCCESSFULLY)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.APPOINTMENT_SLOT_REMOVED_SUCCESSFULLY)).build();
    }
}
