package org.spring.springsecuritydemo.service.notice;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.spring.springsecuritydemo.domain.Notice;
import org.spring.springsecuritydemo.dto.ModifyNoticeDTO;
import org.spring.springsecuritydemo.dto.RegisterNoticeDTO;
import org.spring.springsecuritydemo.response.ApiResponse;
import org.spring.springsecuritydemo.util.Criteria;

import java.util.List;

public interface NoticeService {
    List<Notice> getNotices(Criteria criteria);

    Notice getNotice(Long id, HttpSession session);

    ApiResponse<?> registerNotice(@Valid RegisterNoticeDTO registerNoticeDTO);

    ApiResponse<?> modifyNotice(@Valid ModifyNoticeDTO modifyNoticeDTO);

    ApiResponse<?> deleteNotice(Long id);

    int getTotal();
}
