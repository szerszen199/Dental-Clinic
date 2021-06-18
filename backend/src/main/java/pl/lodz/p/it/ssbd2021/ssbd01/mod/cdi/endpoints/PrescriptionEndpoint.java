package pl.lodz.p.it.ssbd2021.ssbd01.mod.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.EncryptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.DocumentationEntryException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.MedicalDocumentationException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mod.PrescriptionException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.AddDocumentationEntryRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.CreatePrescriptionRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers.PrescriptionsManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.utils.PrescriptionTransactionRepeater;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
}
