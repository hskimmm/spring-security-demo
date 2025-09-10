package org.spring.springsecuritydemo.controller.reply;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.spring.springsecuritydemo.dto.PageDTO;
import org.spring.springsecuritydemo.dto.RegisterReplyDTO;
import org.spring.springsecuritydemo.dto.UpdateReplyDTO;
import org.spring.springsecuritydemo.response.ApiResponse;
import org.spring.springsecuritydemo.service.reply.ReplyService;
import org.spring.springsecuritydemo.util.Criteria;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
@Log4j2
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getReplyList(@PathVariable(value = "id") Long id,
                                                       @ModelAttribute(value = "cri")Criteria criteria) {

        ApiResponse<?> response = replyService.getReplyList(id, criteria);
        PageDTO pageDTO = new PageDTO(criteria, replyService.getReplyTotal(id));

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("replyList", response.getData());
        dataMap.put("page", pageDTO);

        ApiResponse<?> newResponse = new ApiResponse<>(true, response.getMessage(), dataMap);
        return ResponseEntity.ok(newResponse);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ApiResponse<?>> getReply(@PathVariable(value = "id") Long id) {
        ApiResponse<?> response = replyService.getReply(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> registerReply(@Valid @RequestBody RegisterReplyDTO registerReplyDTO) {
        ApiResponse<?> response = replyService.registerReply(registerReplyDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<?>> updateReply(@Valid @RequestBody UpdateReplyDTO updateReplyDTO) {
        ApiResponse<?> response = replyService.updateReply(updateReplyDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteReply(@PathVariable(value = "id") Long id) {
        ApiResponse<?> response = replyService.deleteReply(id);
        return ResponseEntity.ok(response);
    }
}
