package pl.lodz.p.it.ssbd2021.ssbd01.utils;

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
    private Long confirmationJwtExpiration;
    private Long deleteInactiveAccount;



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

    /**
     * Pobiera pole delete inactive account.
     *
     * @return the delete inactive account
     */
    public Long getDeleteInactiveAccount() {
        return deleteInactiveAccount;
    }

    @PostConstruct
    private void loadProperties() {
        Properties prop = null;
        try {
            prop = new Properties();
            InputStream inputStream  = PropertiesLoader.class.getClassLoader()
                    .getResourceAsStream("application.properties");
            if (inputStream != null) {
                prop.load(inputStream);
            }
        } catch (IOException e) {
            // TODO: 18.04.2021
        }
        confirmationJwtSecret = prop.getProperty("confirmation.jwt.secret");
        confirmationJwtExpiration = Long.valueOf(prop.getProperty("confirmation.jwt.expirationMs"));
        deleteInactiveAccount = Long.valueOf(prop.getProperty("delete.inactive.accountMs"));
    }
}
