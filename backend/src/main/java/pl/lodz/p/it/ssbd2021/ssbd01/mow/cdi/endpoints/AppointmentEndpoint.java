package pl.lodz.p.it.ssbd2021.ssbd01.mow.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.request.CreateAppointmentSlotRequestDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.managers.AppointmentManager;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LoggedInAccountUtil;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.converters.AccountConverter;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.converters.AppointmentConverter;

import javax.annotation.security.PermitAll;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
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

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_CREATION_FAILED;


@Path("appointment")
@Stateful
@Interceptors(LogInterceptor.class)
public class AppointmentEndpoint {

    @Inject
    private PropertiesLoader propertiesLoader;

    @Inject
    private AccountManager accountManager;

    @Inject
    private AppointmentManager appointmentManager;

    @Inject
    private LoggedInAccountUtil loggedInAccountUtil;

    /**
     * Tworzy nowe konto.
     *
     * @param appointmentSlotDto obiekt zawierający id doktora oraz datę i godzinę wizyty
     * @return response
     */
    @POST
    @PermitAll
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createAppointmentSlot(@NotNull @Valid CreateAppointmentSlotRequestDTO appointmentSlotDto) {
        int retryTXCounter = propertiesLoader.getTransactionRetryCount();
        boolean rollbackTX = false;
        Exception exception;
        do {
            try {
                exception = null;

                this.appointmentManager.addAppointmentSlot(
                        AppointmentConverter.createAccountEntityFromDto(accountManager.findByLogin(appointmentSlotDto.getDoctorLogin()), appointmentSlotDto)
                );
                rollbackTX = appointmentManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException e) {
                rollbackTX = true;
                exception = e;
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (exception != null) {
            if ((exception instanceof AccountException || exception instanceof MailSendingException)) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
            }
            if (exception instanceof AppBaseException) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(ACCOUNT_CREATION_FAILED)).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.TRANSACTION_FAILED_ERROR)).build();
        }
        return Response.ok().entity(new MessageResponseDto(I18n.ACCOUNT_CREATED_SUCCESSFULLY)).build();
    }
}
