package org.spring.springsecuritydemo.service.notice;

import jakarta.validation.Valid;
import org.spring.springsecuritydemo.domain.Notice;
import org.spring.springsecuritydemo.dto.RegisterNoticeDTO;
import org.spring.springsecuritydemo.response.ApiResponse;

import java.util.List;

public interface NoticeService {
    List<Notice> getNotices();

    Notice getNotice(Long id);

    ApiResponse<?> registerNotice(@Valid RegisterNoticeDTO registerNoticeDTO);
}
