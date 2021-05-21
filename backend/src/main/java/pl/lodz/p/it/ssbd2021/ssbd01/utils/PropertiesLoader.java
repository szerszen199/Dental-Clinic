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
    private String refreshJwtSecret;
    private Long refreshJwtExpiration;
    private Long confirmationJwtExpiration;
    private Long deleteInactiveAccount;
    private String anonymousUserName;


    private String jwtSecret;
    private Long invalidLoginCountBlock;
    private Long jwtExpiration;

    public Long getInvalidLoginCountBlock() {
        return invalidLoginCountBlock;
    }

    public String getRefreshJwtSecret() {
        return refreshJwtSecret;
    }

    public Long getRefreshJwtExpiration() {
        return refreshJwtExpiration;
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

    /**
     * Pobiera pole delete inactive account.
     *
     * @return the delete inactive account
     */
    public Long getDeleteInactiveAccount() {
        return deleteInactiveAccount;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public Long getJwtExpiration() {
        return jwtExpiration;
    }

    public String getAnonymousUserName() {
        return anonymousUserName;
    }

    @PostConstruct
    private void loadProperties() {
        Properties prop = null;
        try {
            prop = new Properties();
            InputStream inputStream = PropertiesLoader.class.getClassLoader()
                    .getResourceAsStream("application.properties");
            if (inputStream != null) {
                prop.load(inputStream);
            }
        } catch (IOException e) {
            //TODO naprawić nie może byc checked exception
            e.printStackTrace();
        }
        confirmationJwtSecret = prop.getProperty("confirmation.jwt.secret");
        jwtSecret = prop.getProperty("jwt.secret");
        confirmationJwtExpiration = Long.valueOf(prop.getProperty("confirmation.jwt.expirationMs"));
        deleteInactiveAccount = Long.valueOf(prop.getProperty("delete.inactive.accountMs"));
        jwtExpiration = Long.valueOf(prop.getProperty("jwt.expirationMs"));
        invalidLoginCountBlock = Long.valueOf(prop.getProperty("invalid.login.count.block"));
        anonymousUserName = prop.getProperty("anonymous.user.name");
        refreshJwtExpiration = Long.valueOf(prop.getProperty("refresh.jwt.expirationMs"));
        refreshJwtSecret = prop.getProperty("refresh.jwt.secret");
    }
}
