package br.com.sh.apiexample.service.impl;

import br.com.sh.apiexample.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(DefaultUserService.class);
}
