package br.com.sh.apiexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomEntityNotFoundException extends RuntimeException {

    public CustomEntityNotFoundException(String message) {
        super(message);
    }

    public CustomEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomEntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
