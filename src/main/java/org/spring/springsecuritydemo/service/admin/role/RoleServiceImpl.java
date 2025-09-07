package org.spring.springsecuritydemo.service.admin.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.spring.springsecuritydemo.domain.Role;
import org.spring.springsecuritydemo.dto.CreateRoleDTO;
import org.spring.springsecuritydemo.mapper.admin.RoleMapper;
import org.spring.springsecuritydemo.response.ApiResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoleServiceImpl implements RoleService{

    private final RoleMapper roleMapper;

    @Transactional(readOnly = true)
    @Override
    public List<Role> getRoles() {
        try {
            return roleMapper.getRoles();
        } catch (DataAccessException e) {
            log.error("권한 목록 조회(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("권한 목록 조회 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("권한 목록 조회(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("권한 목록 조회 중 오류가 발생하였습니다");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRole(Long id) {
        try {
            return roleMapper.getRole(id);
        } catch (DataAccessException e) {
            log.error("권한 상세 조회(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("권한 상세 조회 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("권한 상세 조회(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("권한 상세 조회 중 오류가 발생하였습니다");
        }
    }

    @Transactional
    @Override
    public ApiResponse<?> createRole(CreateRoleDTO createRoleDTO) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            Role role = modelMapper.map(createRoleDTO, Role.class);
            roleMapper.createRole(role);

            return new ApiResponse<>(true, "권한을 등록하였습니다");
        } catch (DataAccessException e) {
            log.error("권한 등록(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("권한 등록 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("권한 등록(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("권한 등록 중 오류가 발생하였습니다");
        }
    }
}
