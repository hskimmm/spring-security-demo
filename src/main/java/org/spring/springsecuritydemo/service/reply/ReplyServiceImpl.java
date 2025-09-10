package org.spring.springsecuritydemo.service.reply;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.domain.Reply;
import org.spring.springsecuritydemo.dto.RegisterReplyDTO;
import org.spring.springsecuritydemo.dto.UpdateReplyDTO;
import org.spring.springsecuritydemo.exception.ReplyNotFoundException;
import org.spring.springsecuritydemo.mapper.reply.ReplyMapper;
import org.spring.springsecuritydemo.response.ApiResponse;
import org.spring.springsecuritydemo.util.Criteria;
import org.spring.springsecuritydemo.util.ModelMapperUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{

    private final ReplyMapper replyMapper;

    @Transactional(readOnly = true)
    @Override
    public ApiResponse<?> getReplyList(Long id, Criteria criteria) {
        try {
            List<Reply> replyList = replyMapper.getReplyList(id, criteria);
            return new ApiResponse<>(true, "전체 댓글 목록을 조회하였습니다", replyList);
        } catch (DataAccessException e) {
            log.error("댓글 목록 조회(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("댓글 목록 조회 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("댓글 목록 조회(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("댓글 목록 조회 중 오류가 발생하였습니다");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ApiResponse<?> getReply(Long id) {
        try {
            Reply reply = replyMapper.getReply(id);
            return new ApiResponse<>(true, "댓글 상세 목록을 가져왔습니다", reply);
        } catch (DataAccessException e) {
            log.error("댓글 상세 조회(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("댓글 상세 조회 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("댓글 상세 조회(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("댓글 상세 조회 중 오류가 발생하였습니다");
        }
    }

    @Transactional
    @Override
    public ApiResponse<?> registerReply(RegisterReplyDTO registerReplyDTO) {
        try {
            Reply reply = Reply.builder()
                    .noticeId(registerReplyDTO.getNoticeId())
                    .accountId(registerReplyDTO.getAccountId())
                    .content(registerReplyDTO.getContent())
                    .username(registerReplyDTO.getUsername())
                    .build();

            replyMapper.registerReply(reply);
            return new ApiResponse<>(true, "댓글을 등록하였습니다");
        } catch (DataAccessException e) {
            log.error("댓글 등록(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("댓글 등록 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("댓글 등록(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("댓글 등록 중 오류가 발생하였습니다");
        }
    }

    @Transactional
    @Override
    public ApiResponse<?> updateReply(UpdateReplyDTO updateReplyDTO) {
        try {
            Reply reply = ModelMapperUtils.map(updateReplyDTO, Reply.class);
            replyMapper.updateReply(reply);

            return new ApiResponse<>(true, "댓글을 수정하였습니다");
        } catch (DataAccessException e) {
            log.error("댓글 수정(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("댓글 수정 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("댓글 수정(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("댓글 수정 중 오류가 발생하였습니다");
        }
    }

    @Transactional
    @Override
    public ApiResponse<?> deleteReply(Long id) {
        if (id == null) {
            throw new ReplyNotFoundException("댓글을 찾을 수 없습니다");
        }

        try {
            replyMapper.deleteReply(id);
            return new ApiResponse<>(true, "댓글을 삭제하였습니다");
        } catch (DataAccessException e) {
            log.error("댓글 삭제(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("댓글 삭제 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("댓글 삭제(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("댓글 삭제 중 오류가 발생하였습니다");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public int getReplyTotal(Long id) {
        try {
            return replyMapper.getReplyTotal(id);
        } catch (DataAccessException e) {
            log.error("댓글 전체 개수 조회(데이터베이스 오류) = {}", e.getMessage());
            throw new RuntimeException("댓글 전체 개수 조회 중 오류가 발생하였습니다");
        } catch (Exception e) {
            log.error("댓글 전체 개수 조회(기타 오류) = {}", e.getMessage());
            throw new RuntimeException("댓글 전체 개수 조회 중 오류가 발생하였습니다");
        }
    }
}
