package ru.itmo.tpl.model;

public class Point {
    private final String link;
    private final String name;

    public Point(String link, String name) {
        this.link = link;
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }
}
