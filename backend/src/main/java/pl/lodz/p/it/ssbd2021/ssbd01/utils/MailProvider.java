package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletContext;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATE_BUTTON;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATE_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATE_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATION_CONFIRMATION_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATION_CONFIRMATION_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_CHANGE_CONFIRM_BUTTON;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_CHANGE_CONFIRM_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_CHANGE_CONFIRM_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_GRANT_ACCESS_LEVEL_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_GRANT_ACCESS_LEVEL_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOCK_BY_ADMIN_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOCK_BY_ADMIN_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOGIN_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOGIN_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_PASSWORD_BY_ADMIN_CONFIRMATION_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_PASSWORD_CONFIRMATION_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_PASSWORD_CONFIRMATION_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_REVOKE_ACCESS_LEVEL_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_REVOKE_ACCESS_LEVEL_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_SCHEDULER_LOCK_BUTTON;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_SCHEDULER_LOCK_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_SCHEDULER_LOCK_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_UNLOCK_BY_ADMIN_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_UNLOCK_BY_ADMIN_TEXT;

/**
 * Klasa Mail provider'a.
 */
@Local
public class MailProvider {

    private Session session;

    // EJB nie bez powodu!
    @EJB
    private MailManager mailManager;

    @Inject
    private ServletContext servletContext;

    @Inject
    private PropertiesLoader propertiesLoader;

    private String getFrom() {
        return propertiesLoader.getAppMailUrl();
    }

    private String getPassword() {
        return propertiesLoader.getAppMailPassword();
    }

    private String getDefaultUrl() {
        return propertiesLoader.getAppDefaultUrl();
    }

    private String getFrontendUrl() {
        return propertiesLoader.getAppFrontendUrl();
    }


    /**
     * Metoda inicjująca parametry serwera SMTP.
     */
    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", propertiesLoader.getMailSmtpHost());
        properties.put("mail.smtp.port", propertiesLoader.getMailSmtpPort());
        properties.put("mail.smtp.ssl.enable", propertiesLoader.getMailSmtpSSLEnable());
        properties.put("mail.smtp.auth", propertiesLoader.getMailSmtpAuth());
        properties.put("mail.smtp.ssl.trust", propertiesLoader.getMailSmtpSSLTrust());

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getFrom(), getPassword());
            }
        };

        session = Session.getInstance(properties, authenticator);
    }

    private String getContextPath() {
        return servletContext.getContextPath() == null ? getDefaultUrl() : servletContext.getContextPath();
    }


    /**
     * Wysyła wiadomość z linkiem aktywacyjnym.
     *
     * @param email Adres, na który zostanie wysłana wiadomość.
     * @param token Login konta, którego link aktywacyjny wysyłamy.
     * @param lang  język wiadomości email
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendActivationMail(String email, String token, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(ACCOUNT_MAIL_ACTIVATE_SUBJECT);
        String activationLink = buildConfirmationLink(getContextPath(), token);
        String messageText =
                paragraph(langBundle.getString(ACCOUNT_MAIL_ACTIVATE_TEXT))
                        + hyperlink(activationLink, langBundle.getString(ACCOUNT_MAIL_ACTIVATE_BUTTON));

        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.activationLink();
        }
    }

    /**
     * Wysyła wiadomość z potwierdzeniem aktywacji konta.
     *
     * @param email Adres, na który zostanie wysłana wiadomość.
     * @param lang  język wiadomości email
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendActivationConfirmationMail(String email, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(ACCOUNT_MAIL_ACTIVATION_CONFIRMATION_SUBJECT);
        String messageText = paragraph(langBundle.getString(ACCOUNT_MAIL_ACTIVATION_CONFIRMATION_TEXT));
        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.activationLink();
        }
    }

    /**
     * Wysyła wiadomość informującą o zablokowanym koncie przez administratora.
     *
     * @param email Adres, na który zostanie wysłana wiadomość.
     * @param lang  język wiadomości email
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendAccountLockByAdminMail(String email, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(ACCOUNT_MAIL_LOCK_BY_ADMIN_SUBJECT);
        String messageText = paragraph(langBundle.getString(ACCOUNT_MAIL_LOCK_BY_ADMIN_TEXT));
        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.accountLock();
        }
    }

    /**
     * Wysyła wiadomość informującą o logowaniu administratora.
     *
     * @param email Adres, na który zostanie wysłana wiadomość.
     * @param lang  język wiadomości email
     * @param ip    język wiadomości adres ip
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    @Asynchronous
    public void sendAdminLoginMail(String email, String lang, String ip) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(ACCOUNT_MAIL_LOGIN_SUBJECT);
        String messageText = paragraph(langBundle.getString(ACCOUNT_MAIL_LOGIN_TEXT)) + ip;
        System.out.println(messageText);
        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.accountLock();
        }
    }

    /**
     * Wysyła wiadomość informującą o odblokowanym koncie przez administratora.
     *
     * @param email Adres, na który zostanie wysłana wiadomość.
     * @param lang  język wiadomości email
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendAccountUnlockByAdminMail(String email, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(ACCOUNT_MAIL_UNLOCK_BY_ADMIN_SUBJECT);
        String messageText = paragraph(langBundle.getString(ACCOUNT_MAIL_UNLOCK_BY_ADMIN_TEXT));
        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.accountLock();
        }
    }


    /**
     * Wysyła wiadomość informującą o zablokowanym koncie po nieudanych logowaniach.
     *
     * @param email Adres, na który zostanie wysłana wiadomość.
     * @param lang  język wiadomości email
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendAccountLockByUnsuccessfulLoginMail(String email, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_SUBJECT);
        String messageText = paragraph(langBundle.getString(ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_TEXT));
        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.accountLock();
        }
    }


    /**
     * Wysyła wiadomość z linkiem potwierdzającym zmianę konta mailowego.
     *
     * @param email Adres, na który zostanie wysłana wiadomość.
     * @param token Login konta, którego link aktywacyjny wysyłamy.
     * @param lang  język wiadomości email
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendEmailChangeConfirmationMail(String email, String token, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(ACCOUNT_MAIL_CHANGE_CONFIRM_SUBJECT);
        String activationLink = buildMailConfirmationLink(getContextPath(), token);
        String messageText =
                paragraph(langBundle.getString(ACCOUNT_MAIL_CHANGE_CONFIRM_TEXT))
                        + hyperlink(activationLink, langBundle.getString(ACCOUNT_MAIL_CHANGE_CONFIRM_BUTTON));

        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.activationLink();
        }
    }

    /**
     * Wysyła mail potwierdzający reset hasła.
     *
     * @param email adres email
     * @param token token
     * @param lang  język wiadomości email
     * @throws MailSendingException wyjątek wysyłania maila
     */
    @Asynchronous
    public void sendResetPassConfirmationMail(String email, String token, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(ACCOUNT_MAIL_PASSWORD_CONFIRMATION_SUBJECT);
        String activationLink = buildResetPassLink(getContextPath(), token);
        String messageText =
                paragraph(langBundle.getString(ACCOUNT_MAIL_PASSWORD_CONFIRMATION_TEXT))
                        + hyperlink(activationLink, langBundle.getString(ACCOUNT_MAIL_CHANGE_CONFIRM_BUTTON));

        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.activationLink();
        }
    }

    /**
     * Wysyła wiadomość z linkiem do zresetowania hasła po zmianie hasła przez administratora.
     *
     * @param email adres email
     * @param token token
     * @param lang  język wiadomości email
     * @throws MailSendingException wyjątek wysyłania maila
     */
    @Asynchronous
    public void sendResetPassByAdminConfirmationMail(String email, String token, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(ACCOUNT_MAIL_PASSWORD_BY_ADMIN_CONFIRMATION_SUBJECT);
        String activationLink = buildResetPassLinkByAdmin(getContextPath(), token);
        String messageText =
                paragraph(langBundle.getString(ACCOUNT_MAIL_PASSWORD_BY_ADMIN_CONFIRMATION_SUBJECT))
                        + hyperlink(activationLink, langBundle.getString(ACCOUNT_MAIL_CHANGE_CONFIRM_BUTTON));

        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.activationLink();
        }
    }

    /**
     * Wysyła wiadomość z linkiem do odblokowania konta po jego automatycznym zablokowaniu z powodu nieaktywności.
     *
     * @param email adres email
     * @param token token
     * @param lang język maila
     * @throws MailSendingException wyjątek wysyłania maila
     */
    public void sendAccountLockedByScheduler(String email, String token, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(ACCOUNT_MAIL_SCHEDULER_LOCK_SUBJECT);
        String activationLink = buildMailUnlockAccount(getContextPath(), token);
        String messageText = paragraph(langBundle.getString(ACCOUNT_MAIL_SCHEDULER_LOCK_TEXT))
                + hyperlink(activationLink, langBundle.getString(ACCOUNT_MAIL_SCHEDULER_LOCK_BUTTON));
        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.passwordResetMail();
        }
    }

    /**
     * Wysyła wiadomość informującą o przyznanym poziomie dostępu dla konta.
     *
     * @param email Adres, na który zostanie wysłana wiadomość.
     * @param level Poziom dostępu, który został przyznany
     * @param lang  język wiadomości email
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendAccountGrantAccessLevelMail(String email, String level, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(ACCOUNT_MAIL_GRANT_ACCESS_LEVEL_SUBJECT);
        try {
            String splittedLevel = level.split("\\.")[1];
            String capitalizedLevel = splittedLevel.substring(0, 1).toUpperCase() + splittedLevel.substring(1);
            String messageText = paragraph(langBundle.getString(ACCOUNT_MAIL_GRANT_ACCESS_LEVEL_TEXT)) + capitalizedLevel;
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException | ArrayIndexOutOfBoundsException e) {
            throw MailSendingException.accountLock();
        }
    }


    /**
     * Wysyła wiadomość informującą o odebranym poziomie dostępu dla konta.
     *
     * @param email Adres, na który zostanie wysłana wiadomość.
     * @param level Poziom dostępu, który został odebrany
     * @param lang  język wiadomości email
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendAccountRevokeAccessLevelMail(String email, String level, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(ACCOUNT_MAIL_REVOKE_ACCESS_LEVEL_SUBJECT);
        try {
            String splittedLevel = level.split("\\.")[1];
            String capitalizedLevel = splittedLevel.substring(0, 1).toUpperCase() + splittedLevel.substring(1);
            String messageText = paragraph(langBundle.getString(ACCOUNT_MAIL_REVOKE_ACCESS_LEVEL_TEXT)) + capitalizedLevel;
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException | ArrayIndexOutOfBoundsException e) {
            throw MailSendingException.accountLock();
        }
    }

    private String paragraph(String text) {
        return "<p>" + text + "</p>";
    }

    private String hyperlink(String link, String content) {
        return "<a href=\"" + link + "\">" + content + "</a>";
    }

    private String buildConfirmationLink(String defaultContext, String token) {
        StringBuilder sb = new StringBuilder(getFrontendUrl());
        sb.append("/activation-confirm/");
        sb.append(token);

        return sb.toString();
    }

    private String buildMailConfirmationLink(String defaultContext, String token) {
        StringBuilder sb = new StringBuilder(getFrontendUrl());
        sb.append("/mail-change-confirm/");
        sb.append(token);

        return sb.toString();
    }

    private String buildMailUnlockAccount(String defaultContext, String token) {
        StringBuilder sb = new StringBuilder(getFrontendUrl());
        sb.append("/unlock-account/");
        sb.append(token);

        return sb.toString();
    }

    private String buildResetPassLink(String defaultContext, String token) {
        StringBuilder sb = new StringBuilder(getFrontendUrl());
        sb.append("/new-password/");
        sb.append(token);

        return sb.toString();
    }

    private String buildResetPassLinkByAdmin(String defaultContext, String token) {
        StringBuilder sb = new StringBuilder(getFrontendUrl());
        sb.append("/new-password-admin/");
        sb.append(token);

        return sb.toString();
    }

}
