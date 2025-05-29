package br.com.sh.apiexample.controller.v1;

import br.com.sh.apiexample.facade.UserFacade;
import br.com.sh.apiexample.model.dto.ExceptionResponseDTO;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.form.UserForm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @Operation(
            summary = "Create User",
            description = "Creates a new user with the provided information.",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class)))
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserDto> create(@RequestBody UserForm userForm) {
        logger.info("Creating user with email: {}", userForm.email());
        UserDto userDto = userFacade.createUser(userForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @Operation(summary = "Get User by CPF", description = "Fetches a user by their CPF",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid CPF format", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponseDTO.class))),
            })
    @GetMapping(path = "/{cpf}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserDto> getUser(@PathVariable String cpf) {
        logger.info("Fetching user with cpf: {}", cpf);
        UserDto userDto = userFacade.getUser(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @Operation(
            summary = "Get All Users",
            description = "Fetches all users in the system.",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class)))
            }
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<UserDto>> getAllUsers() {
        logger.debug("Fetching all users");
        return ResponseEntity.status(HttpStatus.OK).body(userFacade.getAllUsers());
    }

    @Operation(
            summary = "Update User",
            description = "Updates user information based on email and CPF.",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "User updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class)))
            }
    )
    @PatchMapping
    public ResponseEntity<UserDto> update(@RequestParam String email, @RequestParam String cpf) {
        logger.info("Updating user with email: {}", email);
        userFacade.updateUser(email, cpf);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Delete User",
            description = "Deletes a user by CPF.",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid CPF format", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class)))
            }
    )
    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestParam String cpf) {
        logger.info("Deleting user with email: {}", cpf);
        userFacade.deleteUser(cpf);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
