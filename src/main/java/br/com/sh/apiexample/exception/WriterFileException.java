package br.com.sh.apiexample.exception;

public class WriterFileException extends  RuntimeException {
    public WriterFileException(String message) {
        super(message);
    }

    public WriterFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriterFileException(Throwable cause) {
        super(cause);
    }
}
