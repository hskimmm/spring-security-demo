package org.spring.springsecuritydemo.service.admin.usermanagement;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.domain.Account;
import org.spring.springsecuritydemo.dto.UpdateUserDTO;
import org.spring.springsecuritydemo.exception.SamePasswordException;
import org.spring.springsecuritydemo.exception.UserNotFoundException;
import org.spring.springsecuritydemo.mapper.admin.UserManagementMapper;
import org.spring.springsecuritydemo.mapper.user.UserMapper;
import org.spring.springsecuritydemo.response.ApiResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserManagementServiceImpl implements UserManagementService{
    private final UserManagementMapper userManagementMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public List<Account> getUsers() {
        try {
            return userManagementMapper.getUsers();
        } catch (DataAccessException e) {
            log.error("회원관리 조회(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("회원관리 조회 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("회원관리 조회(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("회원관리 조회 중 오류가 발생하였습니다");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Account getUser(String id) {
        if (id == null) {
            throw new UserNotFoundException("회원이 존재하지 않습니다");
        }

        try {
            return userManagementMapper.getUser(id);
        } catch (DataAccessException e) {
            log.error("회원 상세 조회(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("회원 상세 조회 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("회원 상세 조회(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("회원 상세 조회 중 오류가 발생하였습니다");
        }
    }

    @Transactional
    @Override
    public ApiResponse<?> updateUser(UpdateUserDTO updateUserDTO) {
        String oldPassword = userManagementMapper.getPassword(updateUserDTO.getId());
        String newPassword = updateUserDTO.getPassword();

        if (passwordEncoder.matches(newPassword, oldPassword)) {
            throw new SamePasswordException("사용 중인 비밀번호 입니다. 다른 비밀번호를 입력하세요");
        }

        try {
            Account account = Account.builder()
                    .id(updateUserDTO.getId())
                    .username(updateUserDTO.getUsername())
                    .password(passwordEncoder.encode(updateUserDTO.getPassword()))
                    .age(updateUserDTO.getAge())
                    .roles(updateUserDTO.getRoles())
                    .build();

            userManagementMapper.updateUser(account);


            Long accountId = userMapper.getByAccountId(account.getUsername());
            Long roleId = userMapper.getByRoleId(account.getRoles());

            userMapper.roleLogUpdate(accountId, roleId);

            return new ApiResponse<>(true, "회원 정보를 수정하였습니다");
        }  catch (DataAccessException e) {
            log.error("회원 정보 수정(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("회원 정보 수정 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("회원 정보 수정(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("회원 정보 수정 중 오류가 발생하였습니다");
        }
    }
}
