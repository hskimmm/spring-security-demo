package org.spring.springsecuritydemo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    
    @NotNull(message = "회원이 존재하지 않습니다")
    private Long id;
    
    @NotEmpty(message = "아이디를 입력하세요")
    private String username;
    private String password;
    
    @NotNull(message = "나이를 입력하세요")
    private int age;
    
    @NotEmpty(message = "권한을 선택하세요")
    private String roles;
}
