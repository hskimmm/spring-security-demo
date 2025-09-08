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
public class RegisterNoticeDTO {

    private Long accountId;

    @NotEmpty(message = "게시판의 제목을 입력하세요")
    private String title;

    @NotEmpty(message = "게시판의 내용을 입력하세요")
    private String content;

    @NotEmpty(message = "로그인 후 이용 가능합니다")
    private String username;
}
