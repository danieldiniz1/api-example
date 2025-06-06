package br.com.sh.apiexample.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonPropertyOrder({"firstName", "lastName", "email"})
public record UserDto(
        @JsonProperty("email")
        String email,
        @JsonProperty("nome")
        String firstName,
        @JsonProperty("sobrenome")
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        String lastName,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dateResponse,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String phoneNumber) {

}
