package org.spring.springsecuritydemo.service.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.domain.Notice;
import org.spring.springsecuritydemo.exception.NoticeNotFoundException;
import org.spring.springsecuritydemo.mapper.notice.NoticeMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class NoticeServiceImpl implements NoticeService{

    private final NoticeMapper noticeMapper;

    @Transactional(readOnly = true)
    @Override
    public List<Notice> getNotices() {
        try {
            return noticeMapper.getNotices();
        } catch (DataAccessException e) {
            log.error("게시글 조회(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("게시글 조회 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("게시글 조회(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("게시글 조회 중 오류가 발생하였습니다");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Notice getNotice(Long id) {
        if (id == null) {
            throw new NoticeNotFoundException("게시글이 존재하지 않습니다");
        }

        try {
            return noticeMapper.getNotice(id);
        } catch (DataAccessException e) {
            log.error("게시글 상세 조회(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("게시글 상세 조회 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("게시글 상세 조회(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("게시글 상세 조회 중 오류가 발생하였습니다");
        }
    }
}
