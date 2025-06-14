package br.com.sh.apiexample.exception;

public class InvalidFileResourceException extends  RuntimeException {
    public InvalidFileResourceException(String message) {
        super(message);
    }

    public InvalidFileResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
