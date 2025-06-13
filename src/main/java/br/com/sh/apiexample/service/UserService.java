package br.com.sh.apiexample.service;

import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.projection.UserProjectionDto;
import com.jayway.jsonpath.JsonPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserService {

    UserModel save(UserModel userModel);

    UserProjectionDto findBycpf(String cpf);

    void updateUser(String email,String cpf);

    void deleteUser(String cpf);

    List<UserProjectionDto> findallUsers();

    Page<UserModel> findAllUsersPaginated(PageRequest pageRequest);
}
