package pl.lodz.p.it.ssbd2021.ssbd01.security;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

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


    @Override
    public void filter(ContainerRequestContext requestContext) {
        String header = requestContext.getHeaderString("If-Match");
        if (header == null || header.isEmpty()) {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity(I18n.BAD_ETAG_VALUE)
                    .build());

        } else if (!EntityIdentitySignerVerifier.validateEntitySignature(header)) {
            requestContext.abortWith(Response.status(Response.Status.PRECONDITION_FAILED).build());
        }
    }
}
