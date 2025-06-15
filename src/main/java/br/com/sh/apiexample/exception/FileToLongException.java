package br.com.sh.apiexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
public class FileToLongException extends  RuntimeException {

    public FileToLongException(String message) {
        super(message);
    }

    public FileToLongException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileToLongException(Throwable cause) {
        super(cause);
    }
}
