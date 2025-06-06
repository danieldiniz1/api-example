package br.com.sh.apiexample.model.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"firstName", "lastName", "email"})
public record UserProjectionDto(
        @JsonProperty("email")
        String email,
        @JsonProperty("nome")
        String firstName,
        @JsonProperty("sobrenome")
        String lastName) {

}
