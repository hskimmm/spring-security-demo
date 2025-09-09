package org.spring.springsecuritydemo.controller.reply;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.response.ApiResponse;
import org.spring.springsecuritydemo.service.reply.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
@Log4j2
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getReplyList(@PathVariable(value = "id") Long id) {
        ApiResponse<?> response = replyService.getReplyList(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ApiResponse<?>> getReply(@PathVariable(value = "id") Long id) {
        ApiResponse<?> response = replyService.getReply(id);
        return ResponseEntity.ok(response);
    }
}
