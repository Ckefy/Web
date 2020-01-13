package ru.itmo.tpl.model;

public class Post {
    private final long id;
    private final String title;
    private final String text;
    private final long user_id;

    public Post(long id, String title, String text, long user_id) {
        this.id = id;
        this.text = text;
        this.title = title;
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public long getUser_Id() {
        return user_id;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }
}
