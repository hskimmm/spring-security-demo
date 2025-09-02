package org.spring.springsecuritydemo.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.spring.springsecuritydemo.domain.Account;
import org.spring.springsecuritydemo.dto.AccountContext;
import org.spring.springsecuritydemo.dto.AccountDTO;
import org.spring.springsecuritydemo.mapper.user.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
@RequiredArgsConstructor
@Log4j2
public class FormUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = userMapper.loadUserByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("해당 유저가 존재하지 않습니다 = " + username);
        }

        //권한
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(account.getRoles()));

        //사용자 정보
        ModelMapper modelMapper = new ModelMapper();
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);

        return new AccountContext(accountDTO, authorities);
    }
}
