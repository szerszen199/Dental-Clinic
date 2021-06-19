package pl.lodz.p.it.ssbd2021.ssbd01.mow.cdi.endpoints;

import java.util.List;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.CreateAppointmentSlotRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers.AppointmentManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.utils.AppointmentTransactionRepeater;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.converters.AppointmentConverter;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.DoctorRatingException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.DoctorAndRateResponseDTO;

/**
 * Typ AppointmentEndpoint - punkt dostępowy dla zapytań związanych z wizytami i lekarzami.
 */

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_CREATION_FAILED;


@Path("appointment")
@Stateful
@DenyAll
@Interceptors(LogInterceptor.class)
public class AppointmentEndpoint {

    @Inject
    private AppointmentManager appointmentManager;

    @Inject
    private AppointmentTransactionRepeater appointmentTransactionRepeater;

    @Inject
    private PropertiesLoader propertiesLoader;

    @Inject
    private AccountManager accountManager;

    @Inject
    private LoggedInAccountUtil loggedInAccountUtil;

    /**
     * Pobiera listę lekarz i ich ocen.
     *
     * @return lista lekarzy i ich ocen
     */
    @GET
    @RolesAllowed({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.PATIENT})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("doctors")
    public Response getDoctorsAndRates() {
        List<DoctorAndRateResponseDTO> doctors;
        try {
            doctors = appointmentManager.getAllDoctorsAndRates();
        } catch (DoctorRatingException | EJBTransactionRolledbackException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().entity(doctors).build();
    }

    /**
     * Tworzy nowe konto.
     *
     * @param appointmentSlotDto obiekt zawierający id doktora oraz datę i godzinę wizyty
     * @return response
     */
    @POST
    @RolesAllowed({I18n.RECEPTIONIST})
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createAppointmentSlot(@NotNull @Valid CreateAppointmentSlotRequestDTO appointmentSlotDto) {
        try {
            appointmentTransactionRepeater.repeatTransaction(
                    () -> appointmentManager.addAppointmentSlot(AppointmentConverter.createAccountEntityFromDto(
                            accountManager.findByLogin(appointmentSlotDto.getDoctorLogin()), appointmentSlotDto)));
        } catch (AppointmentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.APPOINTMENT_SLOT_CREATION_FAILED)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.APPOINTMENT_SLOT_CREATED_SUCCESSFULLY)).build();
    }
}
