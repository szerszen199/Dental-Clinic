package pl.lodz.p.it.ssbd2021.ssbd01.mod.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.EncryptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.DocumentationEntryException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.AddDocumentationEntryRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.DeleteDocumentationEntryRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers.MedicalDocumentationManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.utils.MedicalDocumentationTransactionRepeater;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("documentation")
@Stateful
@DenyAll
@Interceptors(LogInterceptor.class)
public class DocumentationEndpoint {

    @Inject
    private MedicalDocumentationTransactionRepeater medicalDocumentationTransactionRepeater;

    @Inject
    private MedicalDocumentationManager medicalDocumentationManager;

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
            medicalDocumentationTransactionRepeater.repeatTransaction(
                    () -> medicalDocumentationManager.removeDocumentationEntry(deleteDocumentationEntryRequestDTO.getId()), medicalDocumentationManager);
        } catch (DocumentationEntryException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_DELETION_UNSUCCESSFUL)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_DELETED_SUCCESSFULLY)).build();
    }

    @PUT
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.DOCTOR})
    public Response addDocumentationEntry(@NotNull @Valid AddDocumentationEntryRequestDTO addDocumentationEntryRequestDTO) {
        try {
            medicalDocumentationTransactionRepeater.repeatTransaction(
                    () -> medicalDocumentationManager.addDocumentationEntry(addDocumentationEntryRequestDTO), medicalDocumentationManager);
        } catch (DocumentationEntryException | AccountException | EncryptionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_CREATED_UNSUCCESSFULLY)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.DOCUMENTATION_ENTRY_CREATED_SUCCESSFULLY)).build();
    }


}
