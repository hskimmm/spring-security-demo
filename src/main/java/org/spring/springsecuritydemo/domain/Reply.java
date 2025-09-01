package org.spring.springsecuritydemo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    private Long id;
    private Long noticeId;
    private Long accountId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String username;
}
