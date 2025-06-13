package br.com.sh.apiexample.facade.impl;

import br.com.sh.apiexample.facade.UserFacade;
import br.com.sh.apiexample.facade.mapper.converter.UserConverter;
import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.form.UserForm;
import br.com.sh.apiexample.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultUserFacade implements UserFacade {

    private static Logger logger = LoggerFactory.getLogger(DefaultUserFacade.class);
    private final UserService userService;

    private final UserConverter userConverter;

    public DefaultUserFacade(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @Override
    public UserDto createUser(UserForm form) {
        UserModel user = userConverter.convertToModel(form);
        logger.info("Creating user with email: {}", user.getEmail());
        return userConverter.convertToDto(userService.save(user));
    }

    @Override
    public UserDto getUser(String cpf) {
        return userConverter.convertToDto(userService.findBycpf(cpf));
    }

    @Override
    public void updateUser(String email, String cpf) {
        userService.updateUser(email, cpf);
    }

    @Override
    public void deleteUser(String cpf) {
        userService.deleteUser(cpf);
    }

    @Override
    public Page<UserDto> getAllUsers(PageRequest pageRequest) {
        return userService.findAllUsersPaginated(pageRequest).map(userConverter::convertToDto);
//        return userService.findallUsers().stream().map(userConverter::convertToDto).collect(Collectors.toList());
    }

}
