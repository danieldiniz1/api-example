package br.com.sh.apiexample.service;

import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.projection.UserProjectionDto;

import java.util.List;

public interface UserService {

    UserModel save(UserModel userModel);

    UserProjectionDto findBycpf(String cpf);

    void updateUser(String email,String cpf);

    void deleteUser(String cpf);

    List<UserProjectionDto> findallUsers();
}
