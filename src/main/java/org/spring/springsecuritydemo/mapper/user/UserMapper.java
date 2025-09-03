package org.spring.springsecuritydemo.mapper.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.spring.springsecuritydemo.domain.Account;

@Mapper
public interface UserMapper {
    void userInsert(Account account);

    Long getByAccountId(String username);

    Long getByRoleId(String roles);

    void roleLogInsert(@Param(value = "accountId") Long accountId, @Param(value = "roleId") Long roleId);

    Account loadUserByUsername(String username);

    void roleLogUpdate(@Param(value = "accountId") Long accountId, @Param(value = "roleId") Long roleId);
}
