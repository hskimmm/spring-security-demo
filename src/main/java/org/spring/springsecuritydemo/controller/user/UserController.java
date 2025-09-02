package org.spring.springsecuritydemo.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.dto.CreateUserDTO;
import org.spring.springsecuritydemo.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @GetMapping
    public String signupPage() {
        return "login/signup";
    }

    @PostMapping
    public String signup(@ModelAttribute CreateUserDTO createUserDTO) {
        userService.signup(createUserDTO);
        return "redirect:/login";
    }
}
