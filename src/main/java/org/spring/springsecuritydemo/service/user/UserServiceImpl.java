package org.spring.springsecuritydemo.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.spring.springsecuritydemo.domain.Account;
import org.spring.springsecuritydemo.dto.CreateUserDTO;
import org.spring.springsecuritydemo.mapper.user.UserMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void signup(CreateUserDTO createUserDTO) {
        try {

            Account account = Account.builder()
                    .username(createUserDTO.getUsername())
                    .password(passwordEncoder.encode(createUserDTO.getPassword()))
                    .age(createUserDTO.getAge())
                    .roles(createUserDTO.getRoles())
                    .build();

            //회원가입
            userMapper.userInsert(account);

            //유저 롤 테이블 로그 저장
            Long accountId = userMapper.getByAccountId(account.getUsername());
            Long roleId = userMapper.getByRoleId(account.getRoles());

            userMapper.roleLogInsert(accountId, roleId);

        } catch (DataAccessException e) {
            log.error("회원가입(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("회원가입 중 에러가 발생하였습니다");
        } catch (Exception e) {
            log.error("회원가입(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("회원가입 중 에러가 발생하였습니다");
        }
    }
}
