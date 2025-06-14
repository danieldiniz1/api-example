package br.com.sh.apiexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FileResourceNotFoundException extends  RuntimeException {

    public FileResourceNotFoundException(String message) {
        super(message);
    }

    public FileResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
