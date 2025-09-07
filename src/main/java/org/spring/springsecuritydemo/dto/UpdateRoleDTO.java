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
public class UpdateRoleDTO {
    @NotNull(message = "수정할 권한이 없습니다")
    private Long id;

    @NotEmpty(message = "수정할 권한을 입력하세요")
    private String roleName;
}
