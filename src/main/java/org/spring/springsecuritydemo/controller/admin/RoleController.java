package org.spring.springsecuritydemo.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.domain.Role;
import org.spring.springsecuritydemo.dto.CreateRoleDTO;
import org.spring.springsecuritydemo.dto.UpdateRoleDTO;
import org.spring.springsecuritydemo.response.ApiResponse;
import org.spring.springsecuritydemo.service.admin.role.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
@Log4j2
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String getRoles(Model model) {
        List<Role> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        return "admin/roles";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getRole(@PathVariable Long id, Model model) {
        Role role = roleService.getRole(id);
        model.addAttribute("role", role);
        return "admin/rolesdetails";
    }

    @GetMapping("/roleCreatePage")
    @PreAuthorize("hasRole('ADMIN')")
    public String roleCreatePage() {
        return "admin/roleRegister";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> createRole(@Valid @RequestBody CreateRoleDTO createRoleDTO) {
        ApiResponse<?> response = roleService.createRole(createRoleDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> updateRole(@Valid @RequestBody UpdateRoleDTO updateRoleDTO) {
        ApiResponse<?> response = roleService.updateRole(updateRoleDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> deleteRole(@PathVariable(value = "id") Long id) {
        ApiResponse<?> response = roleService.deleteRole(id);
        return ResponseEntity.ok(response);
    }
}
