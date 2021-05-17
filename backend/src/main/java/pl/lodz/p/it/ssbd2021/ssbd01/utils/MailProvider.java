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
        String subject = "Activate your account!";
        String activationLink = buildConfirmationLink(defaultContext, token);
        String messageText =
                paragraph("Please click link below to verify your account: ")
                        + hyperlink(activationLink, "Activate");

        try {
            sendMail(email, subject, messageText);
        } catch (MessagingException e) {
            throw MailSendingException.activationLink();
        }
    }

    public void sendNewPasswordEmail(String email, String password) throws MailSendingException
    {
        String subject = "New password";
        String messageText = paragraph("Your new password is: ")+ paragraph(password);
        try {
            sendMail(email,subject,messageText);
        }catch (MessagingException e){
            throw MailSendingException.password();
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
