package gov.utleg.bigboard.util;


//import javax.activation.DataHandler;
//import javax.activation.DataSource;
//import javax.activation.FileDataSource;
//import javax.mail.*;
//import javax.mail.Message.RecipientType;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import gov.utleg.bigboard.Constants;
import gov.utleg.bigboard.Env;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import jakarta.mail.internet.MimeMultipart;

@Component
@PropertySource("classpath:application.properties")
public class Email {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Value("${mail.host}")
    String mailHost;

    @Value("${mail.admin.emailAddress}")
    String adminEmailAddress;

    @Value("${spring.profiles.active}")
    String environment;
    
    private Properties setupProps(String from) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", mailHost);
        props.setProperty("mail.smtp.localhost", "domain.com");
        props.setProperty("mail.from", from);
        return props;
    }

    public Boolean send(String from, String recipient, String subject, String message) {
        return send(from, Arrays.asList(recipient), subject, message);
    }

    public Boolean send(String from, List<String> recipients, String subject, String message) {
        try {

            if (from == null || from.isEmpty()) {
                from = adminEmailAddress;
            }
            //if (!environment.equalsIgnoreCase(Constants.ENVIRONMENT_PROD)) {
            if (!Env.getEnv().isProd()) {
                message = Constants.APP_NAME+" EMAIL SERVICE: message rerouted from "+recipients+
                        " due to ENVIRONMENT ["+ Env.getEnv().getServer()+"].\n\n"+
                        Constants.BR+ Constants.BR + message;
                //if (environment.equalsIgnoreCase(Constants.ENVIRONMENT_AT)) {
                if (Env.getEnv().isTest()) {
                    recipients = Arrays.asList(adminEmailAddress);
                } else {
                    recipients = Arrays.asList(adminEmailAddress);
                }
            }

            Session session = Session.getDefaultInstance(setupProps(from), null);

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            if (recipients != null && !recipients.isEmpty()) {
                for (String recipient : recipients) {
                    mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(recipient));
                }
                mimeMessage.setSubject(subject);
                MimeMultipart multipart = new MimeMultipart("related");
                BodyPart messageBodyPart = new MimeBodyPart();
                String messageBody = message;
                messageBodyPart.setContent(messageBody, "text/html");
                multipart.addBodyPart(messageBodyPart);
                mimeMessage.setContent(multipart);
                mimeMessage.saveChanges();

                Transport.send(mimeMessage);

                return Boolean.TRUE;
            }

        } catch (MessagingException e) {
            logger.severe(e.getMessage());
        }

        return Boolean.FALSE;
    }

    public Boolean sendFile(String from, List<String> recipients, String subject, String message, String filePath) {
        // check the file
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File does not exist: "+filePath);
        }
        return sendFile(from, recipients, subject, message, file);
    }

    public Boolean sendFile(String from, List<String> recipients, String subject, String message, File file) {
        return sendFile(from, recipients, subject, message, Arrays.asList(file));
    }

    public Boolean sendFile(String from, List<String> recipients, String subject, String message, List<File> files) {

        if (from == null || from.isEmpty()) {
            from = adminEmailAddress;
        }
        if (!Env.getEnv().isProd()) {
            message = Constants.APP_NAME+" EMAIL SERVICE: message rerouted from "+recipients+
                    " due to ENVIRONMENT ["+ Env.getEnv().getServer()+"] setting.\n\n"+
                    Constants.BR + Constants.BR + message;
            if (Env.getEnv().isTest()) {
                recipients = Arrays.asList(adminEmailAddress);
            } else {
                recipients = Arrays.asList(adminEmailAddress);
            }
        }

        Session session = Session.getDefaultInstance(setupProps(from), null);
        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            if (recipients != null && !recipients.isEmpty()) {
                for (String recipient : recipients) {
                    mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(recipient));
                }
                mimeMessage.setSubject(subject);
                MimeMultipart multipart = new MimeMultipart("related");
                BodyPart messageBodyPart = new MimeBodyPart();
                String messageBody = message;
                messageBodyPart.setContent(messageBody, "text/html");
                multipart.addBodyPart(messageBodyPart);
                mimeMessage.setContent(multipart);
                mimeMessage.saveChanges();

                // attachment(s)
                if (files != null && files.size() > 0) {
                    for (File file : files) {
                        messageBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(file);
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(file.getName());
                        multipart.addBodyPart(messageBodyPart);
                        mimeMessage.setContent(multipart);
                    }
                }

                Transport.send(mimeMessage);

                return Boolean.TRUE;
            }

        } catch (MessagingException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
        }

        return Boolean.FALSE;
    }

}
