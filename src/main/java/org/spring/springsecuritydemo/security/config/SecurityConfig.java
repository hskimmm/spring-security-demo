package org.spring.springsecuritydemo.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/images/**", "/css/**", "/js/**", "/vendor/**", "/favicon.*", "/*/icon-*", "/.well-known/**").permitAll()
                        .requestMatchers("/", "/login", "/signup").permitAll()
                        .requestMatchers("/user").hasAnyAuthority("ROLE_USER")
                        .requestMatchers("/manager").hasAnyAuthority("ROLE_MANAGER")
                        .requestMatchers("/admin").hasAnyAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                )
                .userDetailsService(userDetailsService)
        ;
        return http.build();
    }
}
