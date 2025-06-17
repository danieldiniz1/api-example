package br.com.sh.apiexample.config.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("${email.from}")
    private String emailFrom;

    @Value("${email.subject}")
    private String emailSubject;

    @Value("${email.body}")
    private String emailBody;

    public String getEmailFrom() {
        return emailFrom;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }
}
