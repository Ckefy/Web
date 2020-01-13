package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;
    private final CommentService commentService;

    public PostPage(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @Guest
    @GetMapping(path = "/post/{id}")
    public String postIdGet(@PathVariable("id") String id, Model model) {
        long longId;
        if (id.matches("[0-9]*")) {
            longId = Long.parseLong(id);
            model.addAttribute("post", postService.findById(longId));
        } else {
            model.addAttribute("post", null);
        }
        model.addAttribute("comment", new Comment());
        return "PostPage";
    }

    @PostMapping(path = "/post/{id}")
    public String postAddComment(@PathVariable("id") Long id,
                                   @Valid @ModelAttribute("comment") Comment comment,
                                   BindingResult bindingResult, HttpSession httpSession, Model model) {
        Post post = postService.findById(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "PostPage";
        }
        commentService.save(comment, getUser(httpSession), post);
        return "redirect:/post/{id}";
    }
}
