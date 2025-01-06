package com.devdooly.skeleton.core.service;

import com.devdooly.skeleton.core.dto.User;
import com.devdooly.skeleton.core.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }
}
