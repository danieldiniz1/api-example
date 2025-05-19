package br.com.sh.apiexample.config.handler;

import br.com.sh.apiexample.exception.CustomEntityNotFoundException;
import br.com.sh.apiexample.model.dto.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

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
    public final ResponseEntity<Object> handleItemNotFoundException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDTO(LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)));
    }
}
