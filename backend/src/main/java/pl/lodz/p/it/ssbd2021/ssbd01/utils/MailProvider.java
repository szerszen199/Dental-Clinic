package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.MailSendingException;

import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATE_BUTTON;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATE_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_ACTIVATE_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOCK_BY_ADMIN_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOCK_BY_ADMIN_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_TEXT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_UNLOCK_BY_ADMIN_SUBJECT;
import static pl.lodz.p.it.ssbd2021.ssbd01.common.I18n.ACCOUNT_MAIL_UNLOCK_BY_ADMIN_TEXT;

@ApplicationScoped
public class MailProvider {

    private static final String FROM = "ssbd202101@gmail.com";
    private static final String PASSWORD = "GenWydvam0";
    private static final String DEFAULT_URL = "http://studapp.it.p.lodz.pl:8001";
    private Session session;

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
                return new PasswordAuthentication(FROM, PASSWORD);
            }
        };

        session = Session.getInstance(properties, authenticator);
    }


    /**
     * Wysyła wiadomość z linkiem aktywacyjnym.
     *
     * @param email          Adres, na który zostanie wysłana wiadomość.
     * @param token          Login konta, którego link aktywacyjny wysyłamy.
     * @param defaultContext link aktywacyjny do wysłania na konto.
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendActivationMail(String email, String defaultContext, String token) throws MailSendingException {
        String subject = ACCOUNT_MAIL_ACTIVATE_SUBJECT;
        String activationLink = buildConfirmationLink(defaultContext, token);
        String messageText =
                paragraph(ACCOUNT_MAIL_ACTIVATE_TEXT)
                        + hyperlink(activationLink, ACCOUNT_MAIL_ACTIVATE_BUTTON);

        try {
            sendMail(email, subject, messageText);
        } catch (MessagingException e) {
            throw MailSendingException.activationLink();
        }
    }

    /**
     * Wysyła wiadomość informującą o zablokowanym koncie przez administratora.
     *
     * @param email          Adres, na który zostanie wysłana wiadomość.
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendAccountLockByAdminMail(String email) throws MailSendingException {
        String subject = ACCOUNT_MAIL_LOCK_BY_ADMIN_SUBJECT;
        String messageText = paragraph(ACCOUNT_MAIL_LOCK_BY_ADMIN_TEXT);
        try {
            sendMail(email, subject, messageText);
        } catch (MessagingException e) {
            throw MailSendingException.accountLock();
        }
    }

    /**
     * Wysyła wiadomość informującą o odblokowanym koncie przez administratora.
     *
     * @param email          Adres, na który zostanie wysłana wiadomość.
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendAccounUnlockByAdminMail(String email) throws MailSendingException {
        String subject = ACCOUNT_MAIL_UNLOCK_BY_ADMIN_SUBJECT;
        String messageText = paragraph(ACCOUNT_MAIL_UNLOCK_BY_ADMIN_TEXT);
        try {
            sendMail(email, subject, messageText);
        } catch (MessagingException e) {
            throw MailSendingException.accountLock();
        }
    }


    /**
     * Wysyła wiadomość informującą o zablokowanym koncie po nieudanych logowaniach.
     *
     * @param email          Adres, na który zostanie wysłana wiadomość.
     * @throws MailSendingException Błąd wysyłania wiadomości.
     */
    public void sendAccountLockByUnsuccessfulLoginMail(String email) throws MailSendingException {
        String subject = ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_SUBJECT;
        String messageText = paragraph(ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_TEXT);
        try {
            sendMail(email, subject, messageText);
        } catch (MessagingException e) {
            throw MailSendingException.accountLock();
        }
    }

    private void sendMail(String to, String subject, String mailMessage) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(mailMessage, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    private String paragraph(String text) {
        return "<p>" + text + "</p>";
    }

    private String hyperlink(String link, String content) {
        return "<a href=\"" + link + "\">" + content + "</a>";
    }

    private String buildConfirmationLink(String defaultContext, String token) {
        StringBuilder sb = new StringBuilder(DEFAULT_URL);

        sb.append(defaultContext);
        sb.append("/api/account/confirm?token=");
        sb.append(token);

        return sb.toString();
    }
}
