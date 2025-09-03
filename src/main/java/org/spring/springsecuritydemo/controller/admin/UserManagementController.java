package org.spring.springsecuritydemo.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.domain.Account;
import org.spring.springsecuritydemo.domain.Role;
import org.spring.springsecuritydemo.service.admin.role.RoleService;
import org.spring.springsecuritydemo.service.admin.usermanagement.UserManagementService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Log4j2
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String getUsers(Model model) {
        List<Account> accountList = userManagementService.getUsers();
        model.addAttribute("userList", accountList);
        return "admin/users";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable String id, Model model) {
        Account account = userManagementService.getUser(id);
        model.addAttribute("user", account);

        List<Role> roles = roleService.getRoles();
        model.addAttribute("roleList", roles);
        return "admin/userdetails";
    }
}
