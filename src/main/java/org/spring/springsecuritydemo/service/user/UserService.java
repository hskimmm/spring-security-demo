package org.spring.springsecuritydemo.service.user;

import org.spring.springsecuritydemo.dto.CreateUserDTO;

public interface UserService {
    void signup(CreateUserDTO createUserDTO);
}
