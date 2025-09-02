package org.spring.springsecuritydemo.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication(); //인증된 사용자 객체
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            /*
                세션 무효화
                SecurityContext 비움
                remember-me 쿠키 제거
            */
        }

        return "redirect:/login";
    }
}
