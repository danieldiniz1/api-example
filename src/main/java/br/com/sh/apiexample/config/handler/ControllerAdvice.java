package br.com.sh.apiexample.config.handler;

import br.com.sh.apiexample.exception.*;
import br.com.sh.apiexample.model.dto.ExceptionResponseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Hidden
@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponseDTO(LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)));
    }

    @ExceptionHandler(CustomEntityNotFoundException.class)
    public final ResponseEntity<Object> handleItemNotFoundException(CustomEntityNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDTO(LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)));
    }

    @ExceptionHandler(FileStorageException.class)
    public final ResponseEntity<Object> handleFileStorageException(FileStorageException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponseDTO(LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)));
    }

    @ExceptionHandler(FileResourceNotFoundException.class)
    public final ResponseEntity<Object> handleFileResourceNotFoundException(FileResourceNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponseDTO(LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)));
    }

    @ExceptionHandler(FileToLongException.class)
    public final ResponseEntity<Object> handleFileToLongException(FileToLongException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(new ExceptionResponseDTO(LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)));
    }

    @ExceptionHandler(InvalidFileResourceException.class)
    public final ResponseEntity<ExceptionResponseDTO> handleInvalidFileResourceException(InvalidFileResourceException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDTO(LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)));
    }

    @ExceptionHandler(WriterFileException.class)
    public final ResponseEntity<Object> handleWriterFileException(WriterFileException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponseDTO(LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)));
    }
}
