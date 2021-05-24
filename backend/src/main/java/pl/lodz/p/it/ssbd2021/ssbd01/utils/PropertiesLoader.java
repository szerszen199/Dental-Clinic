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

    private String appMailUrl;
    private String appMailPassword;
    private String appDefaultUrl;

    private String confirmationJwtSecret;
    private String refreshJwtSecret;
    private Long refreshJwtExpiration;
    private Long confirmationJwtExpiration;
    private Long deleteInactiveAccountTimeDelay;
    private String anonymousUserName;
    private String etagSecret;


    private String jwtSecret;
    private Long invalidLoginCountBlock;
    private Long jwtExpiration;
    private String emailChangeConfirmationJWTSecret;
    private Long emailChangeConfirmationJWTExpiration;
    private String resetPasswordConfirmationJwtSecret;
    private Long resetPasswordConfirmationJwtExpiration;

    public String getResetPasswordConfirmationJwtSecret() {
        return resetPasswordConfirmationJwtSecret;
    }

    public Long getResetPasswordConfirmationJwtExpiration() {
        return resetPasswordConfirmationJwtExpiration;
    }

    public String getEmailChangeConfirmationJWTSecret() {
        return emailChangeConfirmationJWTSecret;
    }

    public Long getEmailChangeConfirmationJWTExpiration() {
        return emailChangeConfirmationJWTExpiration;
    }

    public Long getInvalidLoginCountBlock() {
        return invalidLoginCountBlock;
    }

    public String getRefreshJwtSecret() {
        return refreshJwtSecret;
    }

    public Long getRefreshJwtExpiration() {
        return refreshJwtExpiration;
    }

    public String getEtagSecret() {
        return etagSecret;
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
    public Long getDeleteInactiveAccountTimeDelay() {
        return deleteInactiveAccountTimeDelay;
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

    public String getAppMailUrl() {
        return appMailUrl;
    }

    public String getAppMailPassword() {
        return appMailPassword;
    }

    public String getAppDefaultUrl() {
        return appDefaultUrl;
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
        confirmationJwtSecret = prop.getProperty("account.confirmation.jwt.secret");
        jwtSecret = prop.getProperty("jwt.secret");
        confirmationJwtExpiration = Long.valueOf(prop.getProperty("account.confirmation.jwt.expirationMs"));
        deleteInactiveAccountTimeDelay = Long.valueOf(prop.getProperty("delete.inactive.accountMs"));
        jwtExpiration = Long.valueOf(prop.getProperty("jwt.expirationMs"));
        invalidLoginCountBlock = Long.valueOf(prop.getProperty("invalid.login.count.block"));
        anonymousUserName = prop.getProperty("anonymous.user.name");
        refreshJwtExpiration = Long.valueOf(prop.getProperty("refresh.jwt.expirationMs"));
        refreshJwtSecret = prop.getProperty("refresh.jwt.secret");
        emailChangeConfirmationJWTSecret = prop.getProperty("email.change.confirmation.jwt.secret");
        emailChangeConfirmationJWTExpiration = Long.valueOf(prop.getProperty("email.change.confirmation.jwt.expirationMs"));
        resetPasswordConfirmationJwtSecret = prop.getProperty("reset.password.confirmation.jwt.secret");
        resetPasswordConfirmationJwtExpiration = Long.valueOf(prop.getProperty("reset.password.confirmation.jwt.expirationMs"));
        etagSecret = prop.getProperty("etag.secret");
        appDefaultUrl = prop.getProperty("application.default.url");
        appMailUrl = prop.getProperty("application.email_url");
        appMailPassword = prop.getProperty("application.email.password");
    }
}
