package org.spring.springsecuritydemo.controller.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.spring.springsecuritydemo.domain.Notice;
import org.spring.springsecuritydemo.service.notice.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
