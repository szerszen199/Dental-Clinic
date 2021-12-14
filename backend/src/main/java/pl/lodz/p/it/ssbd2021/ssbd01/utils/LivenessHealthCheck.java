package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.net.Socket;


@Liveness
@ApplicationScoped
public class LivenessHealthCheck implements HealthCheck {

    @Inject
    private LivnessSwitch livnessSwitch;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Database connection health check");
        if (livnessSwitch.isAlive()) {
            responseBuilder.up();
        } else {
            responseBuilder.down()
                    .withData("error", "Livness switch set to false");
        }
        return responseBuilder.build();
    }

}
