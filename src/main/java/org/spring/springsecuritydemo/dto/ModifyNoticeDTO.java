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
public class ModifyNoticeDTO {
    @NotNull(message = "게시판을 찾을 수 없습니다")
    private Long id;
    
    @NotEmpty(message = "게시글의 제목을 입력하세요")
    private String title;
    
    @NotEmpty(message = "게시글의 내용을 입력하세요")
    private String content;
    
    @NotEmpty(message = "로그인 후 이용 가능합니다")
    private String username;
}
