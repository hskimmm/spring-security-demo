package org.spring.springsecuritydemo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoleController {

    @GetMapping("/admin/roles")
    public String getRoles() {
        return "admin/roles";
    }
}
