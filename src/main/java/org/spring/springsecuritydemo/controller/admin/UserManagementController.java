package org.spring.springsecuritydemo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserManagementController {

    @GetMapping("/admin/users")
    public String getUsers() {
        return "admin/users";
    }
}
