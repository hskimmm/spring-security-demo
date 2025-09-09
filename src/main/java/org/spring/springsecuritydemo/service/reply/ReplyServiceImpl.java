package org.spring.springsecuritydemo.service.reply;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.domain.Reply;
import org.spring.springsecuritydemo.mapper.reply.ReplyMapper;
import org.spring.springsecuritydemo.response.ApiResponse;
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
    public ApiResponse<?> getReplyList(Long id) {
        try {
            List<Reply> replyList = replyMapper.getReplyList(id);
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
}
