package br.com.sh.apiexample.exception;

import jakarta.mail.MessagingException;

public class EmailSendException extends RuntimeException {

    public EmailSendException(MessagingException e) {
        super("Failed to send email: " + e.getMessage(), e);
    }
}
