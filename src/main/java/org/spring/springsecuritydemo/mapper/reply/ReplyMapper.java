package org.spring.springsecuritydemo.mapper.reply;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.spring.springsecuritydemo.domain.Reply;
import org.spring.springsecuritydemo.util.Criteria;

import java.util.List;

@Mapper
public interface ReplyMapper {
    List<Reply> getReplyList(@Param(value = "id") Long id, @Param(value = "cri") Criteria criteria);

    Reply getReply(Long id);

    void registerReply(Reply reply);

    void updateReply(Reply reply);

    void deleteReply(Long id);

    int getReplyTotal(Long id);
}
