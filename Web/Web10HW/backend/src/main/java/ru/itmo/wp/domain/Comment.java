package ru.itmo.wp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 5000)
    @Lob
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_com;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser_com() {
        return user_com;
    }

    public void setUser_com(User user_com) {
        this.user_com = user_com;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}