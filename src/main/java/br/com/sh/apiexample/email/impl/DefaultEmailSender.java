package br.com.sh.apiexample.email.impl;

import br.com.sh.apiexample.config.email.EmailConfig;
import br.com.sh.apiexample.exception.EmailSendException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

@Component
public class DefaultEmailSender {

    private static final Logger log = LoggerFactory.getLogger(DefaultEmailSender.class);
    private final JavaMailSender mailSender;
    private String to;
    private String subject;
    private Object body;
    private List<InternetAddress> recipients;
    private File attachment;


    public DefaultEmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(EmailConfig config)  {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(config.getEmailFrom());
            helper.setTo(recipients.toArray(new InternetAddress[0]));
            helper.setSubject(subject);
            if (body instanceof String) {
                helper.setText((String) body, true);
            } else {
                helper.setText(body.toString(), true);
            }
            if (!Objects.isNull(attachment)) helper.addAttachment(attachment.getName(), attachment);

            mailSender.send(mimeMessage);
            log.info("Email sent successfully to: {} from {}", recipients, config.getEmailFrom());
        } catch (MessagingException e) {
            log.error("Failed to send email to: {} from {}. Error: {}", recipients, config.getEmailFrom(), e.getMessage());
            throw new EmailSendException(e);

        }


    }

    public DefaultEmailSender to(String to) {
        this.to = to;
        this.recipients = getRecipients(to);
        return this;
    }

    private List<InternetAddress> getRecipients(String to) {
        return getRecipientsFromList(sanitizeEmailList(to));
    }


    public DefaultEmailSender withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public DefaultEmailSender withBody(Object body) {
        this.body = body;
        return this;
    }

    public DefaultEmailSender withAttachment(String fileDirectory) {
        this.attachment = new File(fileDirectory);
        return this;
    }


    private String sanitizeEmailList(String to) {
        Objects.requireNonNull(to);
        return to.replaceAll(" ", "");
    }

    private List<InternetAddress> getRecipientsFromList(String to) {
        StringTokenizer st = new StringTokenizer(to, ",");
        List<InternetAddress> addresses = new java.util.ArrayList<>();
        while (st.hasMoreTokens()) {
            String email = st.nextToken().trim();
            try {
                addresses.add(new InternetAddress(email));
            } catch (Exception e) {
                log.error("Invalid email address: " + email);
            }
        }

        return  addresses;
    }

}
