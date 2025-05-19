package br.com.sh.apiexample.model.dto;

import java.time.LocalDateTime;

public record ExceptionResponseDTO(LocalDateTime timestamp, String errorCode, String details) {
}
