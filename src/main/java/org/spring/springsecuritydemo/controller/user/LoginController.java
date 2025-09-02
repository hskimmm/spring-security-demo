package org.spring.springsecuritydemo.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.spring.springsecuritydemo.dto.AccountDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

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

    @GetMapping("/denied")
    public String denied(@RequestParam(value = "exception", required = false) String exception,
                         @AuthenticationPrincipal AccountDTO accountDTO,
                         Model model) {

        model.addAttribute("exception", exception);
        model.addAttribute("username", accountDTO.getUsername());

        return "login/denied";
    }
}
