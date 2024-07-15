package uz.pdp.backend.service.mailing;

import lombok.Getter;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.Random;


public class MailingService {
    private String senderEmail;
    private String senderPassword;
    private Properties properties;
    private Session session;

    @Getter
    private Integer random;

    public MailingService() {
        this.senderEmail = "009.deagle@gmail.com";
        this.senderPassword = "lhsegjedhwabfccf";

        // Setup mail server properties
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP server
        properties.put("mail.smtp.port", "587");

        // Get the Session object
        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }

    public void sendMessage(String recipientEmail, String subject) {
        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(senderEmail));

            // Set To: header field of the header
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText("Your Verification code: " + random);

            // Send message
            Transport.send(message);

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public void sendPhoto(String recipientEmail, String subject, String messageContent, String photoPath) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);

            // Create the message body part for the text content
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(messageContent);

            // Create the message body part for the photo attachment
            MimeBodyPart photoBodyPart = new MimeBodyPart();
            photoBodyPart.attachFile(photoPath);

            // Create a Multipart object to add the parts to
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(photoBodyPart);

            // Set the content of the message
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Photo sent successfully to " + recipientEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String recipientEmail, String subject, String messageContent, String filePath) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);

            // Create the message body part for the text content
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(messageContent);

            // Create the message body part for the file attachment
            MimeBodyPart fileBodyPart = new MimeBodyPart();
            fileBodyPart.attachFile(filePath);

            // Create a Multipart object to add the parts to
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(fileBodyPart);

            // Set the content of the message
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("File sent successfully to " + recipientEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateRandomNumber(){
        Random r = new Random();
        this.random =  r.nextInt(900000) + 100000;
    }

    public boolean checkCode(int verificationCode) {
        return verificationCode == random;
    }
}

