package org.spring.springsecuritydemo.service.notice;

import org.spring.springsecuritydemo.domain.Notice;

import java.util.List;

public interface NoticeService {
    List<Notice> getNotices();

    Notice getNotice(Long id);
}
