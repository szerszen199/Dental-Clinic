package pl.lodz.p.it.ssbd2021.ssbd01.mow.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.DoctorRatingException;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.AvailableAppointmentResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.DoctorAndRateResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers.AppointmentManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.DoctorRatingException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.RemoveAppointmentSlotRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.AvailableAppointmentResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.DoctorAndRateResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers.AppointmentManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.util.AppointmentTransactionRepeater;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;

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
    private AccountManager accountManager;

    @Inject
    private EntityIdentitySignerVerifier signer;

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
     * Tworzy nowy termin wizyty.
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

    /**
     * Pobiera listę aktywnych pacjentów.
     *
     * @return lista aktywnych pacjentów
     */
    @GET
    @RolesAllowed({I18n.RECEPTIONIST})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("patients")
    public Response getPatients() {
        List<PatientResponseDTO> patients;
        try {
            patients = appointmentManager.getActivePatients();
        } catch (PatientException | EJBTransactionRolledbackException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().entity(patients).build();
    }


    /**
     * Edytuje wizytę.
     *
     * @param appointmentEditRequestDto dane potrzebne do edycji wizyty
     * @param header                    nagłówek If-Match z podpisem obiektu
     * @return 400 jezeli się nie powiodło 200 jeżeli się powiodło
     */
    @POST
    @RolesAllowed({I18n.RECEPTIONIST})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @SignatureFilterBinding
    @Path("edit")
    public Response editAppointment(@NotNull @Valid AppointmentEditRequestDto appointmentEditRequestDto,
                                    @HeaderParam("If-Match") String header) {
        if (!signer.verifyEntityIntegrity(header, appointmentEditRequestDto)) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(DATABASE_OPTIMISTIC_LOCK_ERROR)).build();
        }
        try {
            appointmentTransactionRepeater.repeatTransaction(() -> appointmentManager
                    .editBookedAppointment(appointmentEditRequestDto));
        } catch (AppointmentException e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.APPOINTMENT_EDIT_FAILED)).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.APPOINTMENT_EDIT_SUCCESSFUL)).build();
    }


    /**
     * Pobiera listę wszystkich wolnych przyszłych terminów wizyt.
     *
     * @return lista przyszłych terminów wizyt.
     */
    @GET
    @RolesAllowed({I18n.RECEPTIONIST, I18n.PATIENT})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("available")
    public Response getAllAvailableAppointmentsSlotsList() {
        List<AvailableAppointmentResponseDTO> appointments;
        try {
            appointments = appointmentManager.getAllAppointmentsSlots().stream().map(AvailableAppointmentResponseDTO::new).collect(Collectors.toList());
        } catch (AppointmentException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().entity(appointments).build();
    }

    /**
     * Pobiera listę wolnych przyszłych terminów wizyt dla lekarza  wywołującego metodę.
     *
     * @return lista przyszłych terminów wizyt.
     */
    @GET
    @RolesAllowed(I18n.DOCTOR)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("available-own")
    public Response getOwnAvailableAppointmentsSlotsList() {
        List<AvailableAppointmentResponseDTO> appointments;
        try {
            appointments = appointmentManager.getOwnAppointmentsSlots().stream().map(AvailableAppointmentResponseDTO::new).collect(Collectors.toList());
        } catch (AppointmentException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().entity(appointments).build();
    }

    /**
     * Pobiera listę wszystkich umówionych terminów wizyt.
     *
     * @return DTO z listą wszystkich umówionych wizyt.
     */
    @GET
    @RolesAllowed(I18n.RECEPTIONIST)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all-scheduled-appointments")
    public Response getAllScheduleAppointments() {
        AllScheduledAppointmentsResponseDTO allScheduledAppointmentsResponseDTO;
        try {
            allScheduledAppointmentsResponseDTO = appointmentManager.getScheduledAppointments();
        } catch (AppointmentException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.GET_ALL_SCHEDULED_APPOINTMENTS_FAILED)).build();
        }
        return Response.ok().entity(allScheduledAppointmentsResponseDTO).build();
    }

    /**
     * Pobiera listę wszystkich umówionych terminów wizyt dla zalogowanego lekarza.
     *
     * @return DTO z listą wszystkich umówionych wizyt dla zalogowanego lekarza.
     */
    @GET
    @RolesAllowed(I18n.DOCTOR)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all-scheduled-appointments-by-doctor")
    public Response getAllScheduleAppointmentsByDoctor() {
        AllScheduledAppointmentsResponseDTO allScheduledAppointmentsResponseDTO;
        try {
            allScheduledAppointmentsResponseDTO = appointmentManager.getScheduledAppointmentsByDoctor();
        } catch (AppointmentException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.GET_ALL_SCHEDULED_APPOINTMENTS_FAILED)).build();
        }
        return Response.ok().entity(allScheduledAppointmentsResponseDTO).build();
    }

    /**
     * Pobiera listę wszystkich umówionych terminów wizyt dla zalogowanego pacjenta.
     *
     * @return DTO z listą wszystkich umówionych wizyt dla zalogowanego pacjenta.
     */
    @GET
    @RolesAllowed(I18n.PATIENT)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all-scheduled-appointments-by-patient")
    public Response getAllScheduleAppointmentsByPatient() {
        AllScheduledAppointmentsResponseDTO allScheduledAppointmentsResponseDTO;
        try {
            allScheduledAppointmentsResponseDTO = appointmentManager.getScheduledAppointmentsByPatient();
        } catch (AppointmentException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.GET_ALL_SCHEDULED_APPOINTMENTS_FAILED)).build();
        }
        return Response.ok().entity(allScheduledAppointmentsResponseDTO).build();
    }

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
