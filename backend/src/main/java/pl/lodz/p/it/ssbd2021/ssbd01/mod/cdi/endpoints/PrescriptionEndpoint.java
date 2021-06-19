package pl.lodz.p.it.ssbd2021.ssbd01.mod.cdi.endpoints;

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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.request.DeletePrescriptionRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.ejb.managers.PrescriptionManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mod.utils.PrescriptionTransactionRepeater;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

@Path("prescription")
@Stateful
@DenyAll
@Interceptors(LogInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class PrescriptionEndpoint {
    
    @Inject
    private PrescriptionManager prescriptionManager;
    
    @Inject
    private PrescriptionTransactionRepeater prescriptionTransactionRepeater;

    /**
     * Usuwa receptę.
     *
     * @param deletePrescriptionRequestDTO obiekt DTO przechowujący klucz biznesowy recepty
     * @return {@link Response.Status#OK} w przypadku powodzenia, inaczej {@link Response.Status#BAD_REQUEST}
     */
    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({I18n.DOCTOR})
    public Response removePrescription(@NotNull @Valid DeletePrescriptionRequestDTO deletePrescriptionRequestDTO) {
        try {
            prescriptionTransactionRepeater.repeatTransaction(
                    () -> prescriptionManager.deletePrescription(deletePrescriptionRequestDTO.getBusinessId()),
                    prescriptionManager
            );
        } catch (AppBaseException e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.PRESCRIPTION_REMOVAL_FAILED)).build();
        }

        return Response.ok().entity(new MessageResponseDto(I18n.PRESCRIPTION_REMOVED_SUCCESSFULLY)).build();
    }
}
