package org.spring.springsecuritydemo.service.admin.role;

import jakarta.validation.Valid;
import org.spring.springsecuritydemo.domain.Role;
import org.spring.springsecuritydemo.dto.CreateRoleDTO;
import org.spring.springsecuritydemo.dto.UpdateRoleDTO;
import org.spring.springsecuritydemo.response.ApiResponse;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();

    Role getRole(Long id);

    ApiResponse<?> createRole(CreateRoleDTO createRoleDTO);

    ApiResponse<?> updateRole(@Valid UpdateRoleDTO updateRoleDTO);
}
