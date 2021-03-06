package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
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
import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATE_BUTTON;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATE_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATE_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATION_CONFIRMATION_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATION_CONFIRMATION_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_CHANGE_CONFIRM_BUTTON;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_CHANGE_CONFIRM_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_CHANGE_CONFIRM_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_DELETE_BY_SCHEDULER_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_DELETE_BY_SCHEDULER_TEXT;
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
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.APPOINTMENT_MAIL_CONFIRMED_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.APPOINTMENT_MAIL_CONFIRMED_TEXT;

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
     * Metoda inicjuj??ca parametry serwera SMTP.
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
     * Wysy??a wiadomo???? z linkiem aktywacyjnym.
     *
     * @param email Adres, na kt??ry zostanie wys??ana wiadomo????.
     * @param token Login konta, kt??rego link aktywacyjny wysy??amy.
     * @param lang  j??zyk wiadomo??ci email
     * @throws MailSendingException B????d wysy??ania wiadomo??ci.
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
     * Wysy??a wiadomo???? z potwierdzeniem aktywacji konta.
     *
     * @param email Adres, na kt??ry zostanie wys??ana wiadomo????.
     * @param lang  j??zyk wiadomo??ci email
     * @throws MailSendingException B????d wysy??ania wiadomo??ci.
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
     * Wysy??a wiadomo???? informuj??c?? o zablokowanym koncie przez administratora.
     *
     * @param email Adres, na kt??ry zostanie wys??ana wiadomo????.
     * @param lang  j??zyk wiadomo??ci email
     * @throws MailSendingException B????d wysy??ania wiadomo??ci.
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
     * Wysy??a wiadomo???? informuj??c?? o usuni??ciu kont przez scheduler.
     *
     * @param email Adres, na kt??ry zostanie wys??ana wiadomo????.
     * @param lang  j??zyk wiadomo??ci email
     * @throws MailSendingException B????d wysy??ania wiadomo??ci.
     */
    public void sendAccountDeletedByScheduler(String email, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(ACCOUNT_MAIL_DELETE_BY_SCHEDULER_SUBJECT);
        String messageText = paragraph(langBundle.getString(ACCOUNT_MAIL_DELETE_BY_SCHEDULER_TEXT));
        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.accountLock();
        }
    }

    /**
     * Wysy??a wiadomo???? informuj??c?? o logowaniu administratora.
     *
     * @param email Adres, na kt??ry zostanie wys??ana wiadomo????.
     * @param lang  j??zyk wiadomo??ci email
     * @param ip    j??zyk wiadomo??ci adres ip
     * @throws MailSendingException B????d wysy??ania wiadomo??ci.
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
     * Wysy??a wiadomo???? informuj??c?? o odblokowanym koncie przez administratora.
     *
     * @param email Adres, na kt??ry zostanie wys??ana wiadomo????.
     * @param lang  j??zyk wiadomo??ci email
     * @throws MailSendingException B????d wysy??ania wiadomo??ci.
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
     * Wysy??a wiadomo???? informuj??c?? o zablokowanym koncie po nieudanych logowaniach.
     *
     * @param email Adres, na kt??ry zostanie wys??ana wiadomo????.
     * @param lang  j??zyk wiadomo??ci email
     * @throws MailSendingException B????d wysy??ania wiadomo??ci.
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
     * Wysy??a wiadomo???? z linkiem potwierdzaj??cym zmian?? konta mailowego.
     *
     * @param email Adres, na kt??ry zostanie wys??ana wiadomo????.
     * @param token Login konta, kt??rego link aktywacyjny wysy??amy.
     * @param lang  j??zyk wiadomo??ci email
     * @throws MailSendingException B????d wysy??ania wiadomo??ci.
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
     * Wysy??a mail potwierdzaj??cy reset has??a.
     *
     * @param email adres email
     * @param token token
     * @param lang  j??zyk wiadomo??ci email
     * @throws MailSendingException wyj??tek wysy??ania maila
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
     * Wysy??a wiadomo???? z linkiem do zresetowania has??a po zmianie has??a przez administratora.
     *
     * @param email adres email
     * @param token token
     * @param lang  j??zyk wiadomo??ci email
     * @throws MailSendingException wyj??tek wysy??ania maila
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
     * Wysy??a wiadomo???? z linkiem do odblokowania konta po jego automatycznym zablokowaniu z powodu nieaktywno??ci.
     *
     * @param email adres email
     * @param token token
     * @param lang  j??zyk maila
     * @throws MailSendingException wyj??tek wysy??ania maila
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
     * Wysy??a wiadomo???? informuj??c?? o przyznanym poziomie dost??pu dla konta.
     *
     * @param email Adres, na kt??ry zostanie wys??ana wiadomo????.
     * @param level Poziom dost??pu, kt??ry zosta?? przyznany
     * @param lang  j??zyk wiadomo??ci email
     * @throws MailSendingException B????d wysy??ania wiadomo??ci.
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
     * Wysy??a wiadomo???? informuj??c?? o odebranym poziomie dost??pu dla konta.
     *
     * @param email Adres, na kt??ry zostanie wys??ana wiadomo????.
     * @param level Poziom dost??pu, kt??ry zosta?? odebrany
     * @param lang  j??zyk wiadomo??ci email
     * @throws MailSendingException B????d wysy??ania wiadomo??ci.
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

    /**
     * Wysy??a maila z informacj?? o potwierdzeniu wizyty.
     *
     * @param email Adres, na kt??ry zostanie wys??ana wiadomo????.
     * @param lang  j??zyk wiadomo??ci email
     * @throws MailSendingException B????d wysy??ania wiadomo??ci.
     */
    public void sendAppointmentConfirmedMail(String email, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(APPOINTMENT_MAIL_CONFIRMED_SUBJECT);
        String messageText = paragraph(langBundle.getString(APPOINTMENT_MAIL_CONFIRMED_TEXT));
        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.accountLock();
        }
    }

    /**
     * Wysy??a maila z przypomnieniem o konieczno??ci potwierdzenia wizyty.
     *
     * @param email Adres, na kt??ry zostanie wys??ana wiadomo????.
     * @param lang  j??zyk wiadomo??ci email
     * @throws MailSendingException B????d wysy??ania wiadomo??ci.
     */
    public void sendAppointmentConfirmationReminderMail(String email, String lang) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(I18n.APPOINTMENT_MAIL_CONFIRM_REMINDER_SUBJECT);
        String messageText = paragraph(langBundle.getString(I18n.APPOINTMENT_MAIL_CONFIRM_REMINDER_TEXT));
        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.accountLock();
        }
    }

    /**
     * Wysy??a maila z przypomnieniem o konieczno??ci potwierdzenia wizyty.
     *
     * @param email Adres, na kt??ry zostanie wys??ana wiadomo????.
     * @param lang  j??zyk wiadomo??ci email
     * @param token token
     * @param id    id wizyty do ocenienia
     * @throws MailSendingException B????d wysy??ania wiadomo??ci.
     */
    public void sendAppointmentRateMail(String email, String lang, String token, Long id) throws MailSendingException {
        Locale locale = new Locale(lang);
        ResourceBundle langBundle = ResourceBundle.getBundle("LangResource", locale);
        String subject = langBundle.getString(I18n.APPOINTMENT_RATE_SUBJECT);
        String activationLink = buildRatingLink(getContextPath(), token, id);
        String messageText = paragraph(langBundle.getString(I18n.APPOINTMENT_RATE_TEXT))
                + hyperlink(activationLink, langBundle.getString(I18n.APPOINTMENT_RATE_LINK));
        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.mailFailed();
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

    private String buildRatingLink(String contextPath, String token, Long id) {
        StringBuilder sb = new StringBuilder(getFrontendUrl());
        sb.append("/rate-appointment/").append(id.toString()).append("/").append(token);
        return sb.toString();
    }

}
