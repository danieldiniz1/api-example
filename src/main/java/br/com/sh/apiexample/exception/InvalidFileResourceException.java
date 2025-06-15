package br.com.sh.apiexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFileResourceException extends  RuntimeException {
    public InvalidFileResourceException(String message) {
        super(message);
    }

    public InvalidFileResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
