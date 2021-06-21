package pl.lodz.p.it.ssbd2021.ssbd01.mow.cdi.endpoints;

import java.util.List;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.AppointmentSlotEditRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.CreateAppointmentSlotRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.AppointmentInfoResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers.AppointmentManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.utils.AppointmentTransactionRepeater;
import pl.lodz.p.it.ssbd2021.ssbd01.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignatureFilterBinding;
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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.DoctorRatingException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.DoctorAndRateResponseDTO;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.APPOINTMENT_GET_INFO_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.APPOINTMENT_NOT_FOUND;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.DATABASE_OPTIMISTIC_LOCK_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.PASSWORD_CHANGE_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.TRANSACTION_FAILED_ERROR;

/**
 * Typ AppointmentEndpoint - punkt dostępowy dla zapytań związanych z wizytami i lekarzami.
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

    @Inject
    private EntityIdentitySignerVerifier signer;

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
     * Pobiera informacje o wizycie o {@param id}.
     *
     * @param id id wizyty której chcemy pobrać informacje
     * @return informacje o wizycie
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.RECEPTIONIST, I18n.PATIENT})
    @Path("/info/{id}")
    public Response getAppointmentInfo(@NotNull @PathParam("id") Long id) {
        AppointmentInfoResponseDTO appointment;
        try {
            appointment = new AppointmentInfoResponseDTO(appointmentManager.findById(id));
        } catch (AppointmentException e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(APPOINTMENT_NOT_FOUND)).build();
        } catch (AppBaseException | EJBTransactionRolledbackException e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(APPOINTMENT_GET_INFO_FAILED)).build();
        }
        return Response.ok().entity(appointment).tag(signer.sign(appointment)).build();
    }

    /**
     * Tworzy nową wizytę.
     *
     * @param appointmentSlotDto obiekt zawierający id doktora oraz datę i godzinę wizyty
     * @return response
     */
    @POST
    @RolesAllowed({I18n.RECEPTIONIST})
    @Path("create-slot")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createAppointmentSlot(@NotNull @Valid CreateAppointmentSlotRequestDTO appointmentSlotDto) {
        try {
            appointmentTransactionRepeater.repeatTransaction(
                    () -> appointmentManager.addAppointmentSlot(AppointmentConverter.createAppointmentEntityFromDto(
                            accountManager.findByLogin(appointmentSlotDto.getDoctorLogin()), appointmentSlotDto)));
        } catch (AppointmentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.APPOINTMENT_SLOT_CREATION_FAILED)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.APPOINTMENT_SLOT_CREATED_SUCCESSFULLY)).build();
    }

    /**
     * Edytuje wizytę.
     *
     * @param appointmentSlotEditRequestDto dane potrzebne do edycji terminu wizyty
     * @param header                    nagłówek If-Match z podpisem obiektu
     * @return 400 jezeli się nie powiodło 200 jeżeli się powiodło
     */
    @PUT
    @RolesAllowed({I18n.RECEPTIONIST})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @SignatureFilterBinding
    @Path("edit-slot")
    public Response editAppointment(@NotNull @Valid AppointmentSlotEditRequestDTO appointmentSlotEditRequestDto,
                                    @HeaderParam("If-Match") String header) {
        if (!signer.verifyEntityIntegrity(header, appointmentSlotEditRequestDto)) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(DATABASE_OPTIMISTIC_LOCK_ERROR)).build();
        }

        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;
                appointmentManager.editAppointmentSlot(appointmentSlotEditRequestDto);
                rollbackTX = appointmentManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            }
            catch (Exception e) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.APPOINTMENT_SLOT_EDIT_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);

//        try {
//            //appointmentManager.editAppointmentSlot(appointmentSlotEditRequestDto);
//            appointmentTransactionRepeater.repeatTransaction(
//                    () -> appointmentManager.editAppointmentSlot(appointmentSlotEditRequestDto));
//        } catch (AppointmentException e) {
//            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
//        } catch (Exception e) {
//            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.APPOINTMENT_SLOT_EDIT_FAILED)).build();
//        }
        if (exception != null) {
            if (exception instanceof AppointmentException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            } else if (exception instanceof AppBaseException) {
                return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(PASSWORD_CHANGE_FAILED)).build();
            }
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(TRANSACTION_FAILED_ERROR)).build();
        }

        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.APPOINTMENT_SLOT_EDITED_SUCCESSFULLY)).build();
    }
}
