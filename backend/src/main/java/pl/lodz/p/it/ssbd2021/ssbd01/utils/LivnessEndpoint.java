package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path(("/switch"))
public class LivnessEndpoint {
    @Inject
    LivnessSwitch livnessSwitch;


    @PermitAll
    @GET
    @Path("/health")
    public String switchSwitch() {
        livnessSwitch.setAlive(!livnessSwitch.isAlive());
        return String.valueOf(livnessSwitch.isAlive());
    }
}
