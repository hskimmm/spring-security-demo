package org.spring.springsecuritydemo.service.reply;

import jakarta.validation.Valid;
import org.spring.springsecuritydemo.dto.RegisterReplyDTO;
import org.spring.springsecuritydemo.dto.UpdateReplyDTO;
import org.spring.springsecuritydemo.response.ApiResponse;
import org.spring.springsecuritydemo.util.Criteria;

public interface ReplyService {
    ApiResponse<?> getReplyList(Long id, Criteria criteria);

    ApiResponse<?> getReply(Long id);

    ApiResponse<?> registerReply(@Valid RegisterReplyDTO registerReplyDTO);

    ApiResponse<?> updateReply(@Valid UpdateReplyDTO updateReplyDTO);

    ApiResponse<?> deleteReply(Long id);

    int getReplyTotal(Long id);
}
