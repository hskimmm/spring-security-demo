package org.spring.springsecuritydemo.service.admin.usermanagement;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.domain.Account;
import org.spring.springsecuritydemo.exception.UserNotFoundException;
import org.spring.springsecuritydemo.mapper.admin.UserManagementMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserManagementServiceImpl implements UserManagementService{
    private final UserManagementMapper userManagementMapper;

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
}
