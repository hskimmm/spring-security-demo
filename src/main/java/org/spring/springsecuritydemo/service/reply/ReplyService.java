package org.spring.springsecuritydemo.service.reply;

import org.spring.springsecuritydemo.response.ApiResponse;

public interface ReplyService {
    ApiResponse<?> getReplyList(Long id);

    ApiResponse<?> getReply(Long id);
}
