package pl.lodz.p.it.ssbd2021.ssbd01.security;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Klasa filtra, który sprawdza poprawność nagłówka Etag w żądaniu.
 */
@Provider
@SignatureFilterBinding
public class SignatureFilter implements ContainerRequestFilter {

    @Inject
    EntityIdentitySignerVerifier signerVerifier;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String header = requestContext.getHeaderString("If-Match");
        if (header == null || header.isEmpty()) {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity(new MessageResponseDto(I18n.BAD_ETAG_VALUE))
                    .build());
        } else if (!signerVerifier.validateEntitySignature(header)) {
            requestContext.abortWith(Response.status(Response.Status.PRECONDITION_FAILED).entity(new MessageResponseDto(I18n.ETAG_INVALID)).build());
        }
    }
}
