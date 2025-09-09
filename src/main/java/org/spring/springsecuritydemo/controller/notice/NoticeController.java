package org.spring.springsecuritydemo.controller.notice;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.domain.Notice;
import org.spring.springsecuritydemo.dto.AccountDTO;
import org.spring.springsecuritydemo.dto.ModifyNoticeDTO;
import org.spring.springsecuritydemo.dto.PageDTO;
import org.spring.springsecuritydemo.dto.RegisterNoticeDTO;
import org.spring.springsecuritydemo.response.ApiResponse;
import org.spring.springsecuritydemo.service.notice.NoticeService;
import org.spring.springsecuritydemo.util.Criteria;
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
    /*
         - 2. 페이징
             - Criteria 클래스 설정
             - 쿼리에 페이징 적용
                - 쿼리스트링으로 테스트(?pageNum=2)
             - PageDTO 클래스 설정
             - 전체 데이터 api 설정
             - 프론트에서 페이징 값을 가지고 가공
             - 페이지 번호 클릭 시 마다 해당 페이지로 이동 되게 JS 작업
             - 페이지 이동 시 pageNum 이 같이 물려서 다닐 수 있도록 설정

    */
    private final NoticeService noticeService;

    @GetMapping
    public String getNotices(@ModelAttribute(value = "cri") Criteria criteria, Model model) {
        List<Notice> notices = noticeService.getNotices(criteria);
        model.addAttribute("noticeList", notices);

        PageDTO pageDTO = new PageDTO(criteria, noticeService.getTotal());
        model.addAttribute("page", pageDTO);
        return "notice/noticeList";
    }

    @GetMapping("/{id}")
    public String getNotice(@PathVariable(value = "id") Long id,
                            @AuthenticationPrincipal AccountDTO accountDTO,
                            @ModelAttribute(value = "cri") Criteria criteria,
                            Model model,
                            HttpSession session) {

        Notice notice = noticeService.getNotice(id, session);
        model.addAttribute("notice", notice);
        model.addAttribute("currentUser", accountDTO.getUsername());
        model.addAttribute("accountId", accountDTO.getId());
        return "notice/noticeRead";
    }

    @GetMapping("/regPage")
    public String moveNoticeRegisterPage(@AuthenticationPrincipal AccountDTO accountDTO,
                                         @ModelAttribute(value = "cri") Criteria criteria,
                                         Model model) {

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
    public String moveNoticeModifyPage(@PathVariable(value = "id") Long id,
                                       @AuthenticationPrincipal AccountDTO accountDTO,
                                       @ModelAttribute(value = "cri") Criteria criteria,
                                       Model model,
                                       HttpSession session) {

        Notice notice = noticeService.getNotice(id, session);
        model.addAttribute("notice", notice);
        model.addAttribute("currentUser", accountDTO.getUsername());
        return "notice/noticeModify";
    }

    @PutMapping
    public ResponseEntity<ApiResponse<?>> modifyNotice(@Valid @RequestBody ModifyNoticeDTO modifyNoticeDTO) {
        ApiResponse<?> response = noticeService.modifyNotice(modifyNoticeDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteNotice(@PathVariable(value = "id") Long id) {
        ApiResponse<?> response = noticeService.deleteNotice(id);
        return ResponseEntity.ok(response);
    }
}
