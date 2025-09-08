package org.spring.springsecuritydemo.service.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.domain.Notice;
import org.spring.springsecuritydemo.dto.ModifyNoticeDTO;
import org.spring.springsecuritydemo.dto.RegisterNoticeDTO;
import org.spring.springsecuritydemo.exception.NoticeNotFoundException;
import org.spring.springsecuritydemo.mapper.notice.NoticeMapper;
import org.spring.springsecuritydemo.response.ApiResponse;
import org.spring.springsecuritydemo.util.ModelMapperUtils;
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

    @Transactional
    @Override
    public ApiResponse<?> registerNotice(RegisterNoticeDTO registerNoticeDTO) {
        try {
            Notice notice = ModelMapperUtils.map(registerNoticeDTO, Notice.class);
            noticeMapper.registerNotice(notice);

            return new ApiResponse<>(true, "게시글을 등록하였습니다");
        } catch (DataAccessException e) {
            log.error("게시글 등록(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("게시글 등록 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("게시글 등록(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("게시글 등록 중 오류가 발생하였습니다");
        }
    }

    @Transactional
    @Override
    public ApiResponse<?> modifyNotice(ModifyNoticeDTO modifyNoticeDTO) {
        try {
            Notice notice = ModelMapperUtils.map(modifyNoticeDTO, Notice.class);
            noticeMapper.modifyNotice(notice);

            return new ApiResponse<>(true, "게시글을 수정하였습니다");
        } catch (DataAccessException e) {
            log.error("게시글 수정(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("게시글 수정 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("게시글 수정(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("게시글 수정 중 오류가 발생하였습니다");
        }
    }

    @Transactional
    @Override
    public ApiResponse<?> deleteNotice(Long id) {
        if (id == null) {
            throw new NoticeNotFoundException("게시글이 존재하지 않습니다");
        }

        try {
            noticeMapper.deleteNotice(id);
            return new ApiResponse<>(true, "게시글을 삭제하였습니다");
        } catch (DataAccessException e) {
            log.error("게시글 삭제(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("게시글 삭제 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("게시글 삭제(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("게시글 삭제 중 오류가 발생하였습니다");
        }
    }
}
