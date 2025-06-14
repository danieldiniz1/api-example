package br.com.sh.apiexample.facade;

import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.form.UserForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserFacade {

    UserDto createUser(UserForm form);

    UserDto getUser(String cpf);

    void updateUser(String email, String cpf);

    void deleteUser(String email);

    Page<UserDto> getAllUsers(PageRequest pageRequest);

    List<UserDto> createUsersInBatch(MultipartFile file);
}
