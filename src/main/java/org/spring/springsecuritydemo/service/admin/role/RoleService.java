package org.spring.springsecuritydemo.service.admin.role;

import org.spring.springsecuritydemo.domain.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();

    Role getRole(Long id);
}
