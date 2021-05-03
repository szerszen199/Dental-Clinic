package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import java.util.Properties;
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
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.AccountDto;

public class MailProvider {


    Properties properties = new Properties();
    Session session;
    String address;

    /**
     * Tworzy nowy MailProvider.
     *
     * @param username nazwa użytkownika dla poczty
     * @param password hasło do poczty
     * @param port     numer portu poczty
     * @param host     nazwa hosta poczty
     * @param tls      czy użyuwać tls
     * @param auth     czy używać auth
     */
    public MailProvider(String username, String password, int port, String host, boolean tls, boolean auth) {
        properties.put("mail.smtp.auth", String.valueOf(auth));
        properties.put("mail.smtp.starttls.enable", String.valueOf(tls));
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", String.valueOf(port));
        properties.put("mail.smtp.ssl.trust", host);

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    /**
     * Wysyła wiadomość z linkiem aktywacyjnym.
     *
     * @param accountDto     Konto na adres którego zostanie wysłana wiadomość.
     * @param activationLink link aktywacyjny do wysłania na konto.
     * @throws MessagingException Błąd wysyłania wiadomości.
     */
    public void sendActivationMail(AccountDto accountDto, String activationLink) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(address));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(accountDto.getEmail()));
        message.setSubject("Activate your account");
        String messageText = "Please click link below to verify your account: \n" + activationLink;

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(messageText, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}
