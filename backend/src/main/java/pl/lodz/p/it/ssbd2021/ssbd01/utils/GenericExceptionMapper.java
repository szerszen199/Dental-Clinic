package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;

import javax.ejb.EJBException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<EJBException> {
    @Override
    public Response toResponse(EJBException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new MessageResponseDto(I18n.INTERNAL_SERVER_ERROR)).build();
    }
}
