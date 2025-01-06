package com.devdooly.skeleton.core.config;

import com.devdooly.skeleton.core.repository.UserRepository;
import com.devdooly.skeleton.core.service.UserService;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }

}
