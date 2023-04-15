package com.ranggarifqi.moneymanager.common.email;

import com.ranggarifqi.moneymanager.common.exception.BadRequestException;
import com.ranggarifqi.moneymanager.common.exception.InternalServerException;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class JakartaMailService implements IEmailService{

    @Value("${email.host}")
    private String host;

    @Value("${email.port}")
    private String port;

    @Value("${email.user}")
    private String username;

    @Value("${email.password}")
    private String password;

    @Override
    public void sendSimpleEmail(String recipient, String subject, String body) {
        Session session = this.getSession();
        Message message = new MimeMessage(session);
        try {
            InternetAddress from = new InternetAddress(this.username);
            InternetAddress to = new InternetAddress(recipient);

            message.setFrom(from);
            message.setRecipient(Message.RecipientType.TO, to);
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(body, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (AddressException e) {
            throw new BadRequestException(e.toString());
        } catch (MessagingException e) {
            throw new InternalServerException(e.toString());
        }
    }

    private Properties getProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", this.host);
        prop.put("mail.smtp.port", this.port);
        prop.put("mail.smtp.ssl.trust", this.host);

        return prop;
    }

    private Session getSession() {
        Properties prop = this.getProperties();

        String username = this.username;
        String password = this.password;
        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
