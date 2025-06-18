package br.com.sh.apiexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PdfTemplateNotFoundException extends RuntimeException {

    public PdfTemplateNotFoundException(String s) {
        super(s);
    }
}
