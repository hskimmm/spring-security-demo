package org.spring.springsecuritydemo.mapper.reply;

import org.apache.ibatis.annotations.Mapper;
import org.spring.springsecuritydemo.domain.Reply;

import java.util.List;

@Mapper
public interface ReplyMapper {
    List<Reply> getReplyList(Long id);

    Reply getReply(Long id);

    void registerReply(Reply reply);
}
