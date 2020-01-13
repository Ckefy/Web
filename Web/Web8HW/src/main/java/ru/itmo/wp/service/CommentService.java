package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.repository.CommentRepository;

@Service
public class CommentService {

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    private final CommentRepository commentRepository;

    public void save(Comment comment, User user, Post post) {
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
    }
}