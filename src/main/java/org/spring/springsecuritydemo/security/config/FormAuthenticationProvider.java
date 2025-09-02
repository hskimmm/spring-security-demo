package org.spring.springsecuritydemo.security.config;

import lombok.RequiredArgsConstructor;
import org.spring.springsecuritydemo.dto.AccountContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("authenticationProvider")
@RequiredArgsConstructor
public class FormAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String loginId = authentication.getName();
        String loginPassword = (String) authentication.getCredentials();

        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(loginId);
        if (accountContext == null) {
            throw new UsernameNotFoundException("해당 유저가 존재하지 않습니다 = " + loginId);
        }

        if (!passwordEncoder.matches(loginPassword, accountContext.getPassword())) {
            throw new BadCredentialsException("비밀번호가 다릅니다");
        }

        return new UsernamePasswordAuthenticationToken(accountContext.getAccountDTO(), null, accountContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
