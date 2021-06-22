package pl.lodz.p.it.ssbd2021.ssbd01.mow.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.DoctorRatingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.PatientException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.AppointmentEditRequestDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.AppointmentSlotEditRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.CreateAppointmentSlotRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.AllScheduledAppointmentsResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.AppointmentInfoResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.AvailableAppointmentResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.DoctorAndRateResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.PatientResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response.ScheduledAppointmentResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers.AppointmentManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.utils.AppointmentTransactionRepeater;
import pl.lodz.p.it.ssbd2021.ssbd01.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignatureFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.APPOINTMENT_GET_INFO_FAILED;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.APPOINTMENT_NOT_FOUND;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.DATABASE_OPTIMISTIC_LOCK_ERROR;

/**
 * Typ AppointmentEndpoint - punkt dostępowy dla zapytań związanych z wizytami i lekarzami.
 */
@Path("appointment")
@Stateful
@DenyAll
@Interceptors(LogInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
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
        } catch (DoctorRatingException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(I18n.GET_DOCTORS_AND_RATES_FAILED).build();
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
                    () -> appointmentManager.addAppointmentSlot(appointmentSlotDto));
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
     * Edytuje wizytę.
     *
     * @param appointmentSlotEditRequestDto dane potrzebne do edycji terminu wizyty
     * @param header                        nagłówek If-Match z podpisem obiektu
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
        try {
            appointmentTransactionRepeater.repeatTransaction(
                    () -> appointmentManager.editAppointmentSlot(appointmentSlotEditRequestDto));
        } catch (AppointmentException e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.APPOINTMENT_SLOT_EDIT_FAILED)).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.APPOINTMENT_SLOT_EDITED_SUCCESSFULLY)).build();
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
            List<Appointment> appointments = appointmentManager.getScheduledAppointments();
            List<ScheduledAppointmentResponseDTO> scheduledAppointmentResponseDTOS = new ArrayList<>();
            for (Appointment a : appointments) {
                scheduledAppointmentResponseDTOS.add(new ScheduledAppointmentResponseDTO(a, signer));
            }
            allScheduledAppointmentsResponseDTO = new AllScheduledAppointmentsResponseDTO(scheduledAppointmentResponseDTOS);
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
            List<Appointment> appointments = appointmentManager.getScheduledAppointmentsByDoctor();
            List<ScheduledAppointmentResponseDTO> scheduledAppointmentResponseDTOS = new ArrayList<>();
            for (Appointment a : appointments) {
                scheduledAppointmentResponseDTOS.add(new ScheduledAppointmentResponseDTO(a, signer));
            }
            allScheduledAppointmentsResponseDTO = new AllScheduledAppointmentsResponseDTO(scheduledAppointmentResponseDTOS);
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
            List<Appointment> appointments = appointmentManager.getScheduledAppointmentsByPatient();
            List<ScheduledAppointmentResponseDTO> scheduledAppointmentResponseDTOS = new ArrayList<>();
            for (Appointment a : appointments) {
                scheduledAppointmentResponseDTOS.add(new ScheduledAppointmentResponseDTO(a, signer));
            }
            allScheduledAppointmentsResponseDTO = new AllScheduledAppointmentsResponseDTO(scheduledAppointmentResponseDTOS);
        } catch (AppointmentException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.GET_ALL_SCHEDULED_APPOINTMENTS_FAILED)).build();
        }
        return Response.ok().entity(allScheduledAppointmentsResponseDTO).build();
    }

    /**
     * Potwierdza własną wizytę.
     *
     * @param id id wizyty która ma zostać potwierdzona.
     * @return status powodzenia operacji.
     */
    @GET
    @RolesAllowed({I18n.PATIENT})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("confirm-own/{id}")
    public Response confirmOwnAppointment(@PathParam("id") Long id) {

        try {
            appointmentManager.confirmOwnBookedAppointment(id);
        } catch (AppointmentException | MailSendingException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.APPOINTMENT_CONFIRMED_SUCCESSFULLY)).build();
    }


    /**
     * Potwierdza wizytę.
     *
     * @param id id wizyty która ma zostać potwierdzona.
     * @return status powodzenia operacji.
     */
    @GET
    @RolesAllowed({I18n.RECEPTIONIST})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("confirm/{id}")
    public Response confirmAppointment(@PathParam("id") Long id) {

        try {
            appointmentManager.confirmBookedAppointment(id);
        } catch (AppointmentException | MailSendingException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.APPOINTMENT_CONFIRMED_SUCCESSFULLY)).build();
    }

    /**
     * Ocenia wizytę.
     *
     * @param id   id wizyty która ma zostać potwierdzona.
     * @param mark ocena wizyty,
     * @return status powodzenia operacji.
     */
    @GET
    @RolesAllowed(I18n.PATIENT)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("rate/{id}")
    public Response rateAppointment(@PathParam("id") Long id, Double mark) {
        try {

            appointmentManager.rateAppointment(id, mark);
        } catch (AppointmentException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Status.OK).entity(new MessageResponseDto(I18n.APPOINTMENT_RATED_SUCCESSFULLY)).build();
    }

}
