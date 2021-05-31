package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.Serializable;

/**
 * Klasa menadżera maili.
 */
@Stateless
public class MailManager implements Serializable {

    /**
     * Wyślij email.
     *
     * @param to          do
     * @param subject     tytuł
     * @param from        od
     * @param mailMessage wiadomość
     * @param session     sesja
     * @throws MessagingException wyjątek wysyłania
     */
    @Asynchronous
    public void sendMail(String to, String subject, String from, String mailMessage, Session session) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setHeader("Content-Type", "text/plain; charset=UTF-8");
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(mailMessage, "text/html; charset=UTF-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart, "text/html; charset=UTF-8");

        Transport.send(message);
    }
}
