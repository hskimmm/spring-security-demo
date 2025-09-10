package org.spring.springsecuritydemo.service.reply;

import jakarta.validation.Valid;
import org.spring.springsecuritydemo.dto.RegisterReplyDTO;
import org.spring.springsecuritydemo.dto.UpdateReplyDTO;
import org.spring.springsecuritydemo.response.ApiResponse;

public interface ReplyService {
    ApiResponse<?> getReplyList(Long id);

    ApiResponse<?> getReply(Long id);

    ApiResponse<?> registerReply(@Valid RegisterReplyDTO registerReplyDTO);

    ApiResponse<?> updateReply(@Valid UpdateReplyDTO updateReplyDTO);
}
