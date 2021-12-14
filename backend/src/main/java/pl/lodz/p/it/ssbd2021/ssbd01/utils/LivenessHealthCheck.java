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
    @ConfigProperty(name = "DB_SERVICE_HOST", defaultValue = "mariadb")
    private String host;
    @Inject
    @ConfigProperty(name = "DB_SERVICE_PORT", defaultValue = "3306")
    private int port;

    @Inject
    private LivnessSwitch livnessSwitch;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Database connection health check");
        try {
            pingServer(host, port);
            if (livnessSwitch.isAlive()) {
                responseBuilder.up();
            } else {
                responseBuilder.down()
                        .withData("error", "Livness switch set to false");
            }
        } catch (Exception e) {
            responseBuilder.down()
                    .withData("error", e.getMessage());
        }
        return responseBuilder.build();
    }

    private void pingServer(String dbhost, int port) throws IOException {
        Socket socket = new Socket(dbhost, port);
        socket.close();
    }
}
