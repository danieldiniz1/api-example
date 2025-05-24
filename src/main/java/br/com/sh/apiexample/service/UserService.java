package br.com.sh.apiexample.service;

import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;

public interface UserService {

    UserModel save(UserModel userModel);
}
