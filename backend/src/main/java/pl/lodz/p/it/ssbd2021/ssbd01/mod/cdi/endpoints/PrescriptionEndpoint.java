package pl.lodz.p.it.ssbd2021.ssbd01.mod.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.EncryptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.PrescriptionException;

import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.EditPrescriptionRequestDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.CreatePrescriptionRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.EditPrescriptionRequestDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.PrescriptionResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.PrescriptionInfoDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers.PrescriptionsManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.utils.PrescriptionTransactionRepeater;
import pl.lodz.p.it.ssbd2021.ssbd01.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignatureFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.Encryptor;
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
import java.util.List;


import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.DATABASE_OPTIMISTIC_LOCK_ERROR;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.PRESCRIPTION_NOT_FOUND;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.PRESCRIPTION_GET_INFO_FAILED;

@Path("prescription")
@Stateful
@DenyAll
@Interceptors(LogInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class PrescriptionEndpoint {

    @Inject
    private PrescriptionsManager prescriptionsManager;
    @Inject
    private PrescriptionTransactionRepeater prescriptionTransactionRepeater;
    @Inject
    private EntityIdentitySignerVerifier signer;

    @Inject
    private PropertiesLoader propertiesLoader;

    /**
     * Dodanie recepty dla pacjenta.
     *
     * @param createPrescriptionRequestDTO DTO zawierające odpowiednie dla zapytania informacje.
     * @return {@link Response.Status#OK} przy powodzeniu, inaczej {@link Response.Status#BAD_REQUEST}
     */
    @PUT
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.DOCTOR})
    public Response createPrescription(@NotNull @Valid CreatePrescriptionRequestDTO createPrescriptionRequestDTO) {
        try {
            prescriptionTransactionRepeater.repeatTransaction(
                    () -> prescriptionsManager.createPrescription(createPrescriptionRequestDTO));
        } catch (PrescriptionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.PRESCRIPTION_CREATION_FAILURE)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.PRESCRIPTION_CREATED_SUCCESSFULLY)).build();
    }

    /**
     * Edycja recepty.
     *
     * @param editPrescriptionRequestDto dto z danymi do edycji recepty
     * @param header                     nagłówek If-Match z podpisem obiektu
     * @return {@link Response.Status#OK} przy powodzeniu, inaczej {@link Response.Status#BAD_REQUEST}
     */
    @POST
    @Path("edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.DOCTOR})
    @SignatureFilterBinding
    public Response editPrescription(@NotNull @Valid EditPrescriptionRequestDto editPrescriptionRequestDto,
                                     @HeaderParam("If-Match") String header) {
        if (!signer.verifyEntityIntegrity(header, editPrescriptionRequestDto)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new MessageResponseDto(DATABASE_OPTIMISTIC_LOCK_ERROR)).build();
        }
        try {
            prescriptionsManager.editPrescription(editPrescriptionRequestDto);
        } catch (EncryptionException | PrescriptionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.PRESCRIPTION_EDIT_FAILED)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.PRESCRIPTION_EDITED_SUCCESSFULLY)).build();
    }

    /**
     * Pobiera informacje o recepcie o {@param id}.
     *
     * @param id id recepty której chcemy pobrać informacje
     * @return informacje o wizycie
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.RECEPTIONIST, I18n.PATIENT})
    @Path("/info/{id}")
    public Response getAppointmentInfo(@NotNull @PathParam("id") Long id) {
        PrescriptionInfoDto prescription;
        try {
            prescription = new PrescriptionInfoDto(prescriptionsManager.findById(id), propertiesLoader);
        } catch (PrescriptionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(PRESCRIPTION_NOT_FOUND)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(PRESCRIPTION_GET_INFO_FAILED)).build();
        }
        return Response.ok().entity(prescription).tag(signer.sign(prescription)).build();
    }


    /**
     * Pobiera listę aktywnych pacjentów.
     *
     * @param username the username
     * @return lista aktywnych pacjentów
     */
    @GET
    @RolesAllowed({I18n.DOCTOR})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("prescriptions/doctor/{username}")
    public Response getPrescriptionDoctor(@NotNull @PathParam("username") String username) {
        List<PrescriptionResponseDto> prescriptions;
        try {
            prescriptions = prescriptionsManager.getDoctorPrescriptions(username);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok(prescriptions).build();
    }

    /**
     * Gets prescription patient.
     *
     * @return the prescription patient
     */
    @GET
    @RolesAllowed({I18n.PATIENT})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("prescriptions/patient")
    public Response getPrescriptionPatient() {
        List<PrescriptionResponseDto> prescriptions;
        try {
            prescriptions = prescriptionsManager.getPatientPrescriptions();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok(prescriptions).build();
    }
}
