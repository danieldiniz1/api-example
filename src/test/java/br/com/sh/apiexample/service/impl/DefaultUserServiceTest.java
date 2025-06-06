package br.com.sh.apiexample.service.impl;

import br.com.sh.apiexample.exception.CustomEntityNotFoundException;
import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.projection.UserProjectionDto;
import br.com.sh.apiexample.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @InjectMocks
    private DefaultUserService defaultUserService;

    @Mock
    private UserRepository userRepository;

    private String cpf;
    private String firstName;
    private String lastName;
    private String email;
    private UserModel userModel;
    private UserDto userDto;
    private UserProjectionDto userProjectionDto;

    @BeforeEach
    void setUp() {
        cpf = "12345678900";
        email = "teste@email.com";
        firstName = "John";
        lastName = "Doe";

        userModel = new UserModel.Builder()
                .cpf(cpf)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();

        userProjectionDto =  new UserProjectionDto(email, firstName, lastName);
    }

    @Test
    void ShouldSaveObjectCorrectlyWhenIsNotNull() {
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);
        assertNotNull(defaultUserService.save(new UserModel()));
        verify(userRepository, times(1)).save(any(UserModel.class));
    }

    @Test
    void shouldThrowExceptionWhenSaveObjectIsNull() {
        when(userRepository.save(any(UserModel.class))).thenThrow(new IllegalArgumentException("User cannot be null"));
        assertThrows(Exception.class, () -> {
            defaultUserService.save(null);
        });
    }

    @Test
    void testFindByCpf_UserExists() {
        when(userRepository.findByCpfAndActive(cpf, true)).thenReturn(Optional.of(userProjectionDto));

        UserProjectionDto result = defaultUserService.findBycpf(cpf);

        assertNotNull(result);
        assertEquals(userProjectionDto, result);
        verify(userRepository, times(1)).findByCpfAndActive(cpf, true);
    }

    @Test
    void testFindByCpf_UserNotFound() {

        when(userRepository.findByCpfAndActive(cpf, true)).thenReturn(Optional.empty());

        CustomEntityNotFoundException exception = assertThrows(CustomEntityNotFoundException.class, () -> {
            defaultUserService.findBycpf(cpf);
        });

        assertEquals("User not found with cpf: " + cpf, exception.getMessage());
        verify(userRepository, times(1)).findByCpfAndActive(cpf, true);
    }

    @Test
    void testUpdateUser() {
        doNothing().when(userRepository).updateUser(email, cpf);

        defaultUserService.updateUser(email, cpf);

        verify(userRepository, times(1)).updateUser(email, cpf);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).logicDeleteByCpf(cpf);

        defaultUserService.deleteUser(cpf);

        verify(userRepository, times(1)).logicDeleteByCpf(cpf);
    }

    @Test
    void testFindAllUsers() {
        List<UserProjectionDto> users = List.of(userProjectionDto);
        when(userRepository.findall()).thenReturn(users);

        List<UserProjectionDto> result = defaultUserService.findallUsers();

        assertNotNull(result);
        assertEquals(users.size(), result.size());
        verify(userRepository, times(1)).findall();
    }
}
