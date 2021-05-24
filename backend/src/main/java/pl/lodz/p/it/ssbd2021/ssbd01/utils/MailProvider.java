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
import java.util.Properties;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATE_BUTTON;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATE_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATE_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATION_CONFIRMATION_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATION_CONFIRMATION_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_CHANGE_CONFIRM_BUTTON;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_CHANGE_CONFIRM_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_CHANGE_CONFIRM_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_GENERATED_PASSWORD_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_GENERATED_PASSWORD_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOCK_BY_ADMIN_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOCK_BY_ADMIN_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOGIN_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOGIN_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_PASSWORD_CONFIRMATION_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_PASSWORD_CONFIRMATION_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_UNLOCK_BY_ADMIN_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_UNLOCK_BY_ADMIN_TEXT;

@Local
public class MailProvider {

    // TODO: 24.05.2021 To ma być pobierane z properties loader : )
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


    /**
     * Metoda inicjująca parametry serwera SMTP.
     */
    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

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
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendActivationMail(String email, String token) throws MailSendingException {
        String subject = ACCOUNT_MAIL_ACTIVATE_SUBJECT;
        String activationLink = buildConfirmationLink(getContextPath(), token);
        String messageText =
                paragraph(ACCOUNT_MAIL_ACTIVATE_TEXT)
                        + hyperlink(activationLink, ACCOUNT_MAIL_ACTIVATE_BUTTON);

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
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendActivationConfirmationMail(String email) throws MailSendingException {
        String subject = ACCOUNT_MAIL_ACTIVATION_CONFIRMATION_SUBJECT;
        String messageText = paragraph(ACCOUNT_MAIL_ACTIVATION_CONFIRMATION_TEXT);
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
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendAccountLockByAdminMail(String email) throws MailSendingException {
        String subject = ACCOUNT_MAIL_LOCK_BY_ADMIN_SUBJECT;
        String messageText = paragraph(ACCOUNT_MAIL_LOCK_BY_ADMIN_TEXT);
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
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    @Asynchronous
    public void sendAdminLoginMail(String email) throws MailSendingException {
        String subject = ACCOUNT_MAIL_LOGIN_SUBJECT;
        String messageText = paragraph(ACCOUNT_MAIL_LOGIN_TEXT);
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
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendAccountUnlockByAdminMail(String email) throws MailSendingException {
        String subject = ACCOUNT_MAIL_UNLOCK_BY_ADMIN_SUBJECT;
        String messageText = paragraph(ACCOUNT_MAIL_UNLOCK_BY_ADMIN_TEXT);
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
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendAccountLockByUnsuccessfulLoginMail(String email) throws MailSendingException {
        String subject = ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_SUBJECT;
        String messageText = paragraph(ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_TEXT);
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
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendEmailChangeConfirmationMail(String email, String token) throws MailSendingException {
        String subject = ACCOUNT_MAIL_CHANGE_CONFIRM_SUBJECT;
        String activationLink = buildMailConfirmationLink(getContextPath(), token);
        String messageText =
                paragraph(ACCOUNT_MAIL_CHANGE_CONFIRM_TEXT)
                        + hyperlink(activationLink, ACCOUNT_MAIL_CHANGE_CONFIRM_BUTTON);

        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.activationLink();
        }
    }

    /**
     * Send reset pass confirmation mail.
     *
     * @param email the email
     * @param token the token
     * @throws MailSendingException the mail sending exception
     */
    public void sendResetPassConfirmationMail(String email, String token) throws MailSendingException {
        String subject = ACCOUNT_MAIL_PASSWORD_CONFIRMATION_SUBJECT;
        String activationLink = buildResetPassLink(getContextPath(), token);
        String messageText =
                paragraph(ACCOUNT_MAIL_PASSWORD_CONFIRMATION_TEXT)
                        + hyperlink(activationLink, ACCOUNT_MAIL_CHANGE_CONFIRM_BUTTON);

        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.activationLink();
        }
    }

    /**
     * Send generaterd password mail.
     *
     * @param email the email
     * @param pass  the pass
     * @throws MailSendingException the mail sending exception
     */
    public void sendGeneratedPasswordMail(String email, String pass) throws MailSendingException {
        String subject = ACCOUNT_MAIL_GENERATED_PASSWORD_SUBJECT;
        String messageText =
                paragraph(ACCOUNT_MAIL_GENERATED_PASSWORD_TEXT)
                        + pass;
        try {
            mailManager.sendMail(email, subject, getFrom(), messageText, session);
        } catch (MessagingException e) {
            throw MailSendingException.passwordResetMail();
        }
    }

    private String paragraph(String text) {
        return "<p>" + text + "</p>";
    }

    private String hyperlink(String link, String content) {
        return "<a href=\"" + link + "\">" + content + "</a>";
    }

    private String buildConfirmationLink(String defaultContext, String token) {
        StringBuilder sb = new StringBuilder(getDefaultUrl());

        sb.append(defaultContext);
        sb.append("/api/account/confirm?token=");
        sb.append(token);

        return sb.toString();
    }

    private String buildMailConfirmationLink(String defaultContext, String token) {
        StringBuilder sb = new StringBuilder(getDefaultUrl());

        sb.append(defaultContext);
        sb.append("/api/account/mailconfirm?token=");
        sb.append(token);

        return sb.toString();
    }

    private String buildResetPassLink(String defaultContext, String token) {
        StringBuilder sb = new StringBuilder(getDefaultUrl());

        sb.append(defaultContext);
        sb.append("/api/account/reset?token=");
        sb.append(token);

        return sb.toString();
    }
}
