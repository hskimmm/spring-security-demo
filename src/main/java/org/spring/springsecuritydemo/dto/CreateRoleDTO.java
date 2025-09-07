package org.spring.springsecuritydemo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleDTO {
    @NotEmpty(message = "추가할 권한을 입력하세요")
    private String roleName;
}
