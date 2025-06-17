package br.com.sh.apiexample.service.impl;

import br.com.sh.apiexample.config.email.EmailConfig;
import br.com.sh.apiexample.email.impl.DefaultEmailSender;
import br.com.sh.apiexample.service.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DefaultEmailService implements EmailService {

    private final DefaultEmailSender defaultEmailSender;
    private final EmailConfig emailConfig;

    public  DefaultEmailService(DefaultEmailSender defaultEmailSender, EmailConfig emailConfig) {
        this.defaultEmailSender = defaultEmailSender;
        this.emailConfig = emailConfig;
    }


    @Override
    public void sendEmail(String to, String subject, String body) throws Exception {
        defaultEmailSender
                .to(to)
                .withSubject(subject)
                .withBody(body)
                .sendEmail(emailConfig);
    }

    @Override
    public void sendEmailWithTemplate(String to, String templateName, Object model) throws Exception {

    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) throws Exception {

    }
}
