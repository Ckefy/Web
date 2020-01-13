package ru.itmo.wp.form;

import ru.itmo.wp.domain.Post;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentForm {
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 65000)
    @Lob
    private String text;

    @NotNull
    private Long author;

    @NotNull
    private Post post;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
