package org.spring.springsecuritydemo.mapper.admin;

import org.apache.ibatis.annotations.Mapper;
import org.spring.springsecuritydemo.domain.Role;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> getRoles();

    Role getRole(Long id);

    void createRole(Role role);
}
