package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Typ Properties loader.
 */
@Startup
@Singleton
public class PropertiesLoader {

    private String confirmationJwtSecret;
    private String jwtSecret;
    private Long invalidLoginCountBlock;

    public Long getInvalidLoginCountBlock() {
        return invalidLoginCountBlock;
    }

    /**
     * Pobiera pole confirmation jwt secret.
     *
     * @return confirmation jwt secret
     */
    public String getConfirmationJwtSecret() {
        return confirmationJwtSecret;
    }

    /**
     * Pobiera pole confirmation jwt expiration.
     *
     * @return confirmation jwt expiration
     */
    public Long getConfirmationJwtExpiration() {
        return confirmationJwtExpiration;
    }

    private Long confirmationJwtExpiration;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public Long getJwtExpiration() {
        return jwtExpiration;
    }

    private Long jwtExpiration;

    @PostConstruct
    private void loadProperties() throws AppBaseException {
        Properties prop = null;
        try {
            prop = new Properties();
            InputStream inputStream = PropertiesLoader.class.getClassLoader()
                    .getResourceAsStream("application.properties");
            if (inputStream != null) {
                prop.load(inputStream);
            }
        } catch (IOException e) {
            throw AppBaseException.propertiesError(e);
        }
        confirmationJwtSecret = prop.getProperty("confirmation.jwt.secret");
        jwtSecret = prop.getProperty("jwt.secret");
        confirmationJwtExpiration = Long.valueOf(prop.getProperty("confirmation.jwt.expirationMs"));
        jwtExpiration = Long.valueOf(prop.getProperty("jwt.expirationMs"));
        invalidLoginCountBlock = Long.valueOf(prop.getProperty("invalid.login.count.block"));
    }
}
