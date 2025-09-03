package org.spring.springsecuritydemo.mapper.admin;

import org.apache.ibatis.annotations.Mapper;
import org.spring.springsecuritydemo.domain.Account;

import java.util.List;

@Mapper
public interface UserManagementMapper {
    List<Account> getUsers();

    Account getUser(String id);
}
