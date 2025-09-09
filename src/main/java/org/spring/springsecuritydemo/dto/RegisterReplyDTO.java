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
public class RegisterReplyDTO {

    @NotNull(message = "게시글을 찾을 수 없습니다")
    private Long noticeId;

    @NotNull(message = "로그인 후 이용 가능합니다")
    private Long accountId;

    @NotEmpty(message = "댓글 내용을 입력하세요")
    private String content;
    
    @NotEmpty(message = "로그인 후 이용 가능합니다")
    private String username;
}
