package br.com.sh.apiexample.service;

public interface EmailService {

    void sendEmail(String to, String subject, String body) throws Exception;

    void sendEmailWithTemplate(String to, String templateName, Object model) throws Exception;

    void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) throws Exception;
}
