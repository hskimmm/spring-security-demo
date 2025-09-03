package org.spring.springsecuritydemo.service.admin.usermanagement;

import jakarta.validation.Valid;
import org.spring.springsecuritydemo.domain.Account;
import org.spring.springsecuritydemo.dto.UpdateUserDTO;
import org.spring.springsecuritydemo.response.ApiResponse;

import java.util.List;

public interface UserManagementService {
    List<Account> getUsers();

    Account getUser(String id);

    ApiResponse<?> updateUser(@Valid UpdateUserDTO updateUserDTO);
}
