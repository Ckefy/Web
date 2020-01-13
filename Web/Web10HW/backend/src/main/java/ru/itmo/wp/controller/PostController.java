package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.exception.NoSuchResourceException;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.UserService;
import ru.itmo.wp.util.BindingResultUtils;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class PostController extends ApiController {
    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    public PostController(PostService postService, UserService userService, CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("posts")
    public List<Post> findPosts() {
        return postService.findAll();
    }

    @GetMapping("comments")
    public List<Comment> findComments(@RequestParam Long postId) {
        Post post = postService.findById(postId);
        return post.getComments();
    }

    @GetMapping("allcomments")
    public List<Comment> findAll() {
        return commentService.findAll();
    }

    @PostMapping("posts")
    public void addPost(@RequestBody @Valid PostForm postForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        String title = postForm.getTitle();
        String text = postForm.getText();
        Long author = postForm.getAuthor();
        Post post = new Post();
        post.setText(text);
        post.setTitle(title);
        post.setUser(userService.findById(author));
        postService.addPost(post);
    }

    @PostMapping("posts/comment")
    public void addComment(@RequestBody @Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        String text = commentForm.getText();
        Long author = commentForm.getAuthor();
        Post post = commentForm.getPost();
        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser_com(userService.findById(author));
        comment.setPost(post);
        commentService.addComment(comment);
    }
}
