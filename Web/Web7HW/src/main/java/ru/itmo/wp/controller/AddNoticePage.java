package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeForm;
import ru.itmo.wp.service.NoticeService;

import javax.validation.Valid;

@Controller
public class AddNoticePage extends Page {
    private final NoticeService noticeService;

    public AddNoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice/add")
    public String noticeGet(Model model) {
        model.addAttribute("notice", new NoticeForm());
        return "AddNoticePage";
    }

    @PostMapping("/notice/add")
    public String noticePost(@Valid @ModelAttribute("notice") NoticeForm notice, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "AddNoticePage";
        }
        noticeService.addNotice(notice);
        return "redirect:/";
    }
}