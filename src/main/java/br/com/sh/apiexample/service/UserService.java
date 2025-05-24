package br.com.sh.apiexample.service;

import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;

import java.util.Optional;

public interface UserService {

    UserModel save(UserModel userModel);

    UserDto findBycpf(String cpf);

    void updateUser(String email,String cpf);

    void deleteUser(String cpf);
}
