package br.com.sh.apiexample.controller.v1;

import br.com.sh.apiexample.facade.UserFacade;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.form.UserForm;

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> create(@RequestBody UserForm userForm) {
        logger.info("Creating user with email: {}", userForm.email());
        UserDto userDto = userFacade.createUser(userForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @GetMapping(path = "/{cpf}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUser(@PathVariable String cpf) {
        logger.info("Fetching user with cpf: {}", cpf);
        UserDto userDto = userFacade.getUser(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        logger.debug("Fetching all users");
        return ResponseEntity.status(HttpStatus.OK).body(userFacade.getAllUsers());
    }

    @PatchMapping
    public ResponseEntity<UserDto> update(@RequestParam String email, @RequestParam String cpf) {
        logger.info("Updating user with email: {}", email);
        userFacade.updateUser(email, cpf);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestParam String cpf) {
        logger.info("Deleting user with email: {}", cpf);
        userFacade.deleteUser(cpf);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
