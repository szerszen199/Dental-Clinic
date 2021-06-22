package pl.lodz.p.it.ssbd2021.ssbd01.mow.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.BookAppointmentDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.BookAppointmentSelfDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers.AppointmentManager;
import pl.lodz.p.it.ssbd2021.ssbd01.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * The type Reservation endpoint.
 */
@Path("/reservation")
@Stateful
@DenyAll
@Interceptors(LogInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ReservationEndpoint {

    @Inject
    AppointmentManager appointmentManagerImplementation;

    @Inject
    EntityIdentitySignerVerifier signer;

    /**
     * Reserve appointment response.
     *
     * @param bookAppointmentSelfDto the book appointment self dto
     * @param header                 the header
     * @return the response
     */
    @PUT
    @Path("reserve/self")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.PATIENT})
    public Response reserveAppointmentSelf(@NotNull @Valid BookAppointmentSelfDto bookAppointmentSelfDto, @HeaderParam("If-Match") String header) {
        try {
            appointmentManagerImplementation.bookAppointmentSelf(bookAppointmentSelfDto);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_CREATED_UNSUCCESSFULLY)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_CREATED_SUCCESSFULLY)).build();
    }

    @PUT
    @Path("reserve")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.RECEPTIONIST})
    public Response reserveAppointment(@NotNull @Valid BookAppointmentDto bookAppointmentDto) {
        try {
            appointmentManagerImplementation.bookAppointment(bookAppointmentDto);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_CREATED_UNSUCCESSFULLY)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_CREATED_SUCCESSFULLY)).build();
    }
}
