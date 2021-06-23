package pl.lodz.p.it.ssbd2021.ssbd01.mod.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.EncryptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.DocumentationEntryException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.MedicalDocumentationException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.AddDocumentationEntryRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.DeleteDocumentationEntryRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.EditDocumentationEntryRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.GetFullDocumentationRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.DocumentationEntryResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.DocumentationInfoResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers.DocumentationEntryManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers.MedicalDocumentationManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.utils.DocumentationEntryTransactionRepeater;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.utils.MedicalDocumentationTransactionRepeater;
import pl.lodz.p.it.ssbd2021.ssbd01.security.EntityIdentitySignerVerifier;
import pl.lodz.p.it.ssbd2021.ssbd01.security.SignatureFilterBinding;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.Encryptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.DATABASE_OPTIMISTIC_LOCK_ERROR;

@Path("documentation")
@Stateful
@DenyAll
@Interceptors(LogInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class DocumentationEndpoint {

    @Inject
    private MedicalDocumentationTransactionRepeater medicalDocumentationTransactionRepeater;

    @Inject
    private EntityIdentitySignerVerifier entityIdentitySignerVerifier;

    @Inject
    private DocumentationEntryTransactionRepeater documentationEntryTransactionRepeater;

    @Inject
    private MedicalDocumentationManager medicalDocumentationManager;

    @Inject
    private DocumentationEntryManager documentationEntryManager;

    @Inject
    private EntityIdentitySignerVerifier signer;

    @Inject
    private PropertiesLoader propertiesLoader;

    /**
     * Usuwanie wpisu w dokumentacji medycznej pacjenta.
     *
     * @param deleteDocumentationEntryRequestDTO DTO zawierające ID usuwanego wpisu.
     * @return {@link Response.Status#OK} przy powodzeniu, inaczej {@link Response.Status#BAD_REQUEST}
     */
    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.DOCTOR})
    public Response deleteDocumentationEntry(@NotNull @Valid DeleteDocumentationEntryRequestDTO deleteDocumentationEntryRequestDTO) {
        try {
            medicalDocumentationTransactionRepeater.repeatTransaction(() -> medicalDocumentationManager.removeDocumentationEntry(deleteDocumentationEntryRequestDTO.getId()));
        } catch (DocumentationEntryException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_DELETION_UNSUCCESSFUL)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_DELETED_SUCCESSFULLY)).build();
    }

    /**
     * Dodanie wpisu w dokumentacji medycznej pacjenta.
     *
     * @param addDocumentationEntryRequestDTO DTO zawierające niezbędne informacje do utworzenia wpisu dokumentacji medycznej.
     * @return {@link Response.Status#OK} przy powodzeniu, inaczej {@link Response.Status#BAD_REQUEST}
     */
    @PUT
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.DOCTOR})
    public Response addDocumentationEntry(@NotNull @Valid AddDocumentationEntryRequestDTO addDocumentationEntryRequestDTO) {
        try {
            medicalDocumentationTransactionRepeater.repeatTransaction(
                    () -> medicalDocumentationManager.addDocumentationEntry(addDocumentationEntryRequestDTO));
        } catch (DocumentationEntryException | AccountException | EncryptionException | MedicalDocumentationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_CREATED_UNSUCCESSFULLY)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_CREATED_SUCCESSFULLY)).build();
    }

    /**
     * Edycja wpisu w dokumentacji medycznej pacjenta.
     *
     * @param editDocumentationEntryRequestDTO DTO zawierające niezbędne informacje do edycji wpisu dokumentacji medycznej.
     * @param header                           etag
     * @return {@link Response.Status#OK} przy powodzeniu, inaczej {@link Response.Status#BAD_REQUEST}
     */
    @POST
    @Path("edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.DOCTOR})
    @SignatureFilterBinding
    public Response editDocumentationEntry(@NotNull @Valid EditDocumentationEntryRequestDTO editDocumentationEntryRequestDTO, @HeaderParam("If-Match") String header) {
        if (!signer.verifyEntityIntegrity(header, editDocumentationEntryRequestDTO)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(DATABASE_OPTIMISTIC_LOCK_ERROR)).build();
        }
        try {
            documentationEntryTransactionRepeater.repeatTransaction(
                    () -> documentationEntryManager.editDocumentationEntry(editDocumentationEntryRequestDTO));
        } catch (EncryptionException | DocumentationEntryException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_EDITED_UNSUCCESSFULLY)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_EDITED_SUCCESSFULLY)).build();
    }

    /**
     * Pobiera pole pełną dokumentację medyczną użytkownika.
     *
     * @param getFullDocumentationRequestDTO DTO dla zapytania.
     * @return {@link Response.Status#OK} w przypadku powodzenia, inaczej {@link Response.Status#BAD_REQUEST}
     */
    @POST
    @Path("get-all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.DOCTOR})
    // TODO: 17.06.2021 Potrzebuje żeby jakaś osoba mądrzejsza odemnie wytłumaczyła mi,
    //  w jakis sposób zwracany jest odpowiedni komunikat a wyjątek jest łapany a mimo to dostaje stacktrace na konsole
    public Response getFullDocumentationForUser(@NotNull @Valid GetFullDocumentationRequestDTO getFullDocumentationRequestDTO) {
        Encryptor encryptor = new Encryptor(propertiesLoader);
        try {
            return Response.ok()
                    .entity(new DocumentationInfoResponseDTO(
                            medicalDocumentationManager.getDocumentationByPatient(getFullDocumentationRequestDTO.getPatient()),
                            documentationEntryManager.getDocumentationEntriesForUser(getFullDocumentationRequestDTO.getPatient()),
                            encryptor,
                            entityIdentitySignerVerifier))
                    .build();
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.MEDICAL_DOCUMENTATION_FETCH_FAILED)).build();
        } catch (MedicalDocumentationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.MEDICAL_DOCUMENTATION_NOT_FOUND)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.MEDICAL_DOCUMENTATION_FETCH_FAILED)).build();
        }
    }

    /**
     * Pobiera pole wpisu dokumentacji medycznej użytkownika.
     *
     * @param id id wpisu dokumentacji
     * @return {@link Response.Status#OK} w przypadku powodzenia, inaczej {@link Response.Status#BAD_REQUEST}
     */
    @GET
    @Path("get/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.DOCTOR})
    public Response getDocumentationEntry(@NotNull @PathParam("id") Long id) {
        Encryptor encryptor = new Encryptor(propertiesLoader);
        try {
            return Response.ok()
                    .entity(new DocumentationEntryResponseDTO(
                            documentationEntryManager.getDocumentationEntry(id),
                            encryptor,
                            entityIdentitySignerVerifier))
                    .build();
        } catch (DocumentationEntryException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_NOT_FOUND)).build();
        }
    }


}
