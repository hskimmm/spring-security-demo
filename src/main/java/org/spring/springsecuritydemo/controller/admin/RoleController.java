package org.spring.springsecuritydemo.controller.admin;

import lombok.RequiredArgsConstructor;
import org.spring.springsecuritydemo.domain.Role;
import org.spring.springsecuritydemo.service.admin.role.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public String getRoles(Model model) {
        List<Role> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        return "admin/roles";
    }

    @GetMapping("/{id}")
    public String getRole(@PathVariable Long id, Model model) {
        Role role = roleService.getRole(id);
        model.addAttribute("role", role);
        return "admin/rolesdetails";
    }
}
