package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Klasa loader właściwości.
 */
@Startup
@Singleton
public class PropertiesLoader {

    private String appMailUrl;
    private String appMailPassword;
    private String appDefaultUrl;
    private String appFrontendUrl;

    private String confirmationJwtSecret;
    private String refreshJwtSecret;
    private String unlockByMailConfirmationJwtSecret;
    private Long refreshJwtExpiration;
    private Long confirmationJwtExpiration;
    private Long deleteInactiveAccountTimeDelay;
    private Long deactivateInactiveAccountTimeDelay;
    private String anonymousUserName;
    private String etagSecret;
    private String cipherKey;
    private String cipherType;
    private String jwtSecret;
    private Long invalidLoginCountBlock;
    private Long jwtExpiration;
    private String emailChangeConfirmationJWTSecret;
    private Long emailChangeConfirmationJWTExpiration;
    private String resetPasswordConfirmationJwtSecret;
    private Long resetPasswordConfirmationJwtExpiration;
    private int transactionRetryCount;
    private String mailSmtpHost;
    private String mailSmtpPort;
    private String mailSmtpSSLEnable;
    private String mailSmtpAuth;
    private String mailSmtpSSLTrust;

    public String getCipherType() {
        return cipherType;
    }

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

    public int getTransactionRetryCount() {
        return transactionRetryCount;
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

    public Long getDeactivateInactiveAccountTimeDelay() {
        return deactivateInactiveAccountTimeDelay;
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

    public String getMailSmtpHost() {
        return mailSmtpHost;
    }

    public String getMailSmtpPort() {
        return mailSmtpPort;
    }

    public String getMailSmtpSSLEnable() {
        return mailSmtpSSLEnable;
    }

    public String getMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public String getMailSmtpSSLTrust() {
        return mailSmtpSSLTrust;
    }

    public String getUnlockByMailConfirmationJwtSecret() {
        return unlockByMailConfirmationJwtSecret;
    }

    public String getAppFrontendUrl() {
        return appFrontendUrl;
    }

    public String getCipherKey() {
        return cipherKey;
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
            e.printStackTrace();
        }
        confirmationJwtSecret = prop.getProperty("account.confirmation.jwt.secret");
        jwtSecret = prop.getProperty("jwt.secret");
        confirmationJwtExpiration = Long.valueOf(prop.getProperty("account.confirmation.jwt.expirationMs"));
        deleteInactiveAccountTimeDelay = Long.valueOf(prop.getProperty("delete.inactive.accountMs"));
        deactivateInactiveAccountTimeDelay = Long.valueOf(prop.getProperty("deactivate.inactive.accountMs"));
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
        cipherKey = prop.getProperty("cipher.key");
        cipherType = prop.getProperty("cipher.type");
        transactionRetryCount = Integer.parseInt(prop.getProperty("transaction.retry.count"));
        appDefaultUrl = prop.getProperty("application.default.url");
        appMailUrl = prop.getProperty("application.email_url");
        appMailPassword = prop.getProperty("application.email.password");
        mailSmtpHost = prop.getProperty("mail.smtp.host");
        mailSmtpPort = prop.getProperty("mail.smtp.port");
        mailSmtpSSLEnable = prop.getProperty("mail.smtp.ssl.enable");
        mailSmtpAuth = prop.getProperty("mail.smtp.auth");
        mailSmtpSSLTrust = prop.getProperty("mail.smtp.ssl.trust");
        unlockByMailConfirmationJwtSecret = prop.getProperty("unlock.by.mail.confirmation.jwt.secret");
        appFrontendUrl = prop.getProperty("application.frontend.url");
    }
}
