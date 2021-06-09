package pl.lodz.p.it.ssbd2021.ssbd01.security;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.ForbiddenException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class ErrorHandlingFilter implements ClientResponseFilter {

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        Response.Status status = Response.Status.fromStatusCode(responseContext.getStatus());
        switch (status) {
            case INTERNAL_SERVER_ERROR:
//                clientException = new PermissionException(message);
                break;
            case NOT_FOUND:
//                clientException = new MyNotFoundException(message);
                break;
            case FORBIDDEN:
                throw new ForbiddenException();
        }


    }


}
