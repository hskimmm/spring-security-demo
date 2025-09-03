package org.spring.springsecuritydemo.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.domain.Account;
import org.spring.springsecuritydemo.service.admin.usermanagement.UserManagementService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Log4j2
public class UserManagementController {

    private final UserManagementService userManagementService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String getUsers(Model model) {
        List<Account> accountList = userManagementService.getUsers();
        log.info("accountList = {}", accountList);
        model.addAttribute("userList", accountList);
        return "admin/users";
    }
}
