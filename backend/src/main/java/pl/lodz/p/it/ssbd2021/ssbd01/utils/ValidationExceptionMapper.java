package pl.lodz.p.it.ssbd2021.ssbd01.utils;


import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Klasa mapowania wyjątków walidacji.
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        var message = exception.getMessage();
        var arg0 = Arrays.stream(message.strip().split(":|,")).filter(s -> !s.contains("arg0")).findFirst();
        if (arg0.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(arg0.get())).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(I18n.BEAN_VALIDATION_ERROR)).build();
    }
}