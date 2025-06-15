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
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true",methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping("/v1/user")
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
//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"}, maxAge = 3600, allowCredentials = "true", methods = {RequestMethod.POST})
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
            description = "Fetches all users in the system with pagination support. Use query params: page (default 0), pageSize (default 10), sort (ASC or DESC, default DESC).",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class)))
            }
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    método que retorna apenas Page e tem mais detalhamento via interface, mas pode ter problema de serialização com versão do spring boot
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10", name = "pageSize") int pageSize,
            @RequestParam(defaultValue = "DESC") String sort
    ) {
        logger.debug("Fetching all users with pagination: page={}, pageSize={}, sort={}", page, pageSize, sort);
        Page<UserDto> usersPage = userFacade.getAllUsers(PageRequest.of(page, pageSize, Sort.by(sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "email", "firstName")));
        return ResponseEntity.status(HttpStatus.OK).body(usersPage);
    }
//
//    @Operation(
//            summary = "Get All Users",
//            description = "Fetches all users in the system with pagination support. Use query params: page (default 0), pageSize (default 10), sort (ASC or DESC, default DESC).",
//            tags = {"User"},
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))),
//                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class)))
//            }
//    )
    //    método que retorna apenas PagedModel<EntityModel<UserDto>> e tem menos detalhamento via interface, mas evita ter problema de serialização com versão do spring boot por ser módulo do spring HATEOAS
//    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public ResponseEntity<PagedModel<EntityModel<UserDto>>> getAllUsers(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10", name = "pageSize") int pageSize,
//            @RequestParam(defaultValue = "DESC") String sort,
//            PagedResourcesAssembler<UserDto> pagedResourcesAssembler
//    ) {
//        logger.debug("Fetching all users with pagination: page={}, pageSize={}, sort={}", page, pageSize, sort);
//        Page<UserDto> usersPage = userFacade.getAllUsers(PageRequest.of(page, pageSize, Sort.Direction.fromString(sort),"email"));
//        PagedModel<EntityModel<UserDto>> model = pagedResourcesAssembler.toModel(usersPage);
//        return ResponseEntity.status(HttpStatus.OK).body(model);
//    }

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

    @Operation(
            summary = "Create Users in Batch",
            description = "Cria múltiplos usuários a partir de um arquivo enviado (importação em lote).",
            tags = {"User"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuários criados com sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Arquivo inválido ou erro de validação",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Requisição não autenticada",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Requisição não autorizada",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno ao processar o arquivo",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponseDTO.class))
                    )
            }
    )
    @PostMapping(value = "/batch",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<UserDto>> createUserInBatch(@RequestParam("file") MultipartFile file){
        logger.info("Creating users in batch from file: {}", file.getOriginalFilename());
        List<UserDto> userDtos = userFacade.createUsersInBatch(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtos);
    }

    @GetMapping(path = "/download-data",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<Resource> downloadFileData(@RequestParam(required = true,name = "contentType") String contentType,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10", name = "pageSize") int pageSize,
                                                     @RequestParam(defaultValue = "DESC") String sort) {
        logger.info("Downloading file with content type: {}", contentType);
        Resource resource = userFacade.downloadFileData(contentType,PageRequest
                .of(page, pageSize, Sort.by(sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "email", "firstName")));
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .header("Content-Disposition", "attachment; filename=\"users." + contentType + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }


}
