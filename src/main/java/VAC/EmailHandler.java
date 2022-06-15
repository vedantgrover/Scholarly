package VAC;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Properties;

public class EmailHandler {

    private final Session session;

    public EmailHandler() {
        final Properties properties = System.getProperties();

        String host = "smtp.gmail.com";
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("scholarlyreplybot@gmail.com", Dotenv.load().get("SCHOLARLY_PASSWORD"));
            }
        });

        session.setDebug(true);
    }

    public void sendEmail(String to, String subject, String messageToSend) {
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            String from = "scholarlyreplybot@gmail.com";
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.addRecipient(MimeMessage.RecipientType.CC, new InternetAddress("vedantvgrover@gmail.com"));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(messageToSend);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mxe) {
            mxe.printStackTrace();
        }
    }
}
