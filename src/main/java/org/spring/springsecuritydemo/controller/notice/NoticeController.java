package org.spring.springsecuritydemo.controller.notice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.domain.Notice;
import org.spring.springsecuritydemo.dto.AccountDTO;
import org.spring.springsecuritydemo.dto.ModifyNoticeDTO;
import org.spring.springsecuritydemo.dto.RegisterNoticeDTO;
import org.spring.springsecuritydemo.response.ApiResponse;
import org.spring.springsecuritydemo.service.notice.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
@Log4j2
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String getNotices(Model model) {
        List<Notice> notices = noticeService.getNotices();
        model.addAttribute("noticeList", notices);
        return "notice/noticeList";
    }

    @GetMapping("/{id}")
    public String getNotice(@PathVariable(value = "id") Long id, Model model) {
        Notice notice = noticeService.getNotice(id);
        model.addAttribute("notice", notice);
        return "notice/noticeRead";
    }

    @GetMapping("/regPage")
    public String moveNoticeRegisterPage(@AuthenticationPrincipal AccountDTO accountDTO, Model model) {
        model.addAttribute("username", accountDTO.getUsername());
        model.addAttribute("accountId", accountDTO.getId());
        return "notice/noticeRegister";
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> registerNotice(@Valid @ModelAttribute RegisterNoticeDTO registerNoticeDTO) {
        ApiResponse<?> response = noticeService.registerNotice(registerNoticeDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/modify/{id}")
    public String moveNoticeModifyPage(@PathVariable(value = "id") Long id, Model model) {
        Notice notice = noticeService.getNotice(id);
        model.addAttribute("notice", notice);
        return "notice/noticeModify";
    }

    @PutMapping
    public ResponseEntity<ApiResponse<?>> modifyNotice(@Valid @RequestBody ModifyNoticeDTO modifyNoticeDTO) {
        ApiResponse<?> response = noticeService.modifyNotice(modifyNoticeDTO);
        return ResponseEntity.ok(response);
    }
}
