package org.spring.springsecuritydemo.mapper.notice;

import org.apache.ibatis.annotations.Mapper;
import org.spring.springsecuritydemo.domain.Notice;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<Notice> getNotices();
}
