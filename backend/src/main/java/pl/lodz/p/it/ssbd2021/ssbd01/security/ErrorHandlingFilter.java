package pl.lodz.p.it.ssbd2021.ssbd01.security;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.annotation.Annotation;

@Provider
public class ErrorHandlingFilter implements ContainerResponseFilter {


    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        Response.Status status = Response.Status.fromStatusCode(responseContext.getStatus());
        switch (status) {
            case INTERNAL_SERVER_ERROR:
                responseContext.setEntity(new MessageResponseDto(I18n.INTERNAL_SERVER_ERROR), new Annotation[]{}, MediaType.valueOf(MediaType.APPLICATION_JSON));
                break;
            case NOT_FOUND:
                responseContext.setEntity(new MessageResponseDto(I18n.NOT_FOUND), new Annotation[]{}, MediaType.valueOf(MediaType.APPLICATION_JSON));
                break;
            case FORBIDDEN:
                responseContext.setEntity(new MessageResponseDto(I18n.FORBIDDEN), new Annotation[]{}, MediaType.valueOf(MediaType.APPLICATION_JSON));
                break;
            default:
        }
    }
}
