package pl.lodz.p.it.ssbd2021.ssbd01.mok.cdi.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.MessageResponseDto;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Typ Readiness endpoint testowy, do sprawdzania połączenia z backendem.
 */
@Path("readiness")
public class Readiness {

    /**
     * Endpoint zwracający odpowiedź OK.
     *
     * @return response
     */
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response isReady() {
        return Response.status(Response.Status.OK).entity(new MessageResponseDto("ready")).build();
    }



}
