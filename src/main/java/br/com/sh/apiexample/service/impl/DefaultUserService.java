package br.com.sh.apiexample.service.impl;

import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.repository.UserRepository;
import br.com.sh.apiexample.service.UserService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
}
