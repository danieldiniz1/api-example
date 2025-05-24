package br.com.sh.apiexample.facade;

import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.form.UserForm;

public interface UserFacade {

    UserDto createUser(UserForm form);

    UserDto getUser(String cpf);

    void updateUser(String email, String cpf);

    void deleteUser(String email);
}
