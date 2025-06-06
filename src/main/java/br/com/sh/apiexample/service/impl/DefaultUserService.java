package br.com.sh.apiexample.service.impl;

import br.com.sh.apiexample.exception.CustomEntityNotFoundException;
import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.projection.UserProjectionDto;
import br.com.sh.apiexample.repository.UserRepository;
import br.com.sh.apiexample.service.UserService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUserService implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(DefaultUserService.class);
    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public UserProjectionDto findBycpf(String cpf) {
        return userRepository
                .findByCpfAndActive(cpf, true)
                .orElseThrow(() -> new CustomEntityNotFoundException("User not found with cpf: " + cpf));
    }

    @Transactional
    @Override
    public void updateUser(String email, String cpf) {
        userRepository.updateUser(email,cpf);
    }

    @Transactional
    @Override
    public void deleteUser(String cpf) {
        userRepository.logicDeleteByCpf(cpf);
    }

    @Override
    public List<UserProjectionDto> findallUsers() {
        return userRepository.findall();
    }
}
