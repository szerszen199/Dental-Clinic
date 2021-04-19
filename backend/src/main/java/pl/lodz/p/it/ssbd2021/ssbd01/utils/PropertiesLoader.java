package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Typ Properties loader.
 */
public class PropertiesLoader {

    private static String confirmationJwtSecret;

    /**
     * Pobiera pole confirmation jwt secret.
     *
     * @return confirmation jwt secret
     */
    public static String getConfirmationJwtSecret() {
        return confirmationJwtSecret;
    }

    /**
     * Pobiera pole confirmation jwt expiration.
     *
     * @return confirmation jwt expiration
     */
    public static Long getConfirmationJwtExpiration() {
        return confirmationJwtExpiration;
    }

    private static Long confirmationJwtExpiration;

    private static final PropertiesLoader INSTANCE = new PropertiesLoader();

    private PropertiesLoader() {
        loadProperties();
    }

    private static void loadProperties() {
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
    }
}
