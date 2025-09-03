package org.spring.springsecuritydemo.service.admin.usermanagement;

import org.spring.springsecuritydemo.domain.Account;

import java.util.List;

public interface UserManagementService {
    List<Account> getUsers();

    Account getUser(String id);
}
