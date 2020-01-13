package ru.itmo.tpl.util;

import ru.itmo.tpl.model.Point;
import ru.itmo.tpl.model.Post;
import ru.itmo.tpl.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirayanov", "Mikhail Mirzayanov", User.Colors.RED),
            new User(2, "tourist", "Genady Korotkevich", User.Colors.BLUE),
            new User(3, "emusk", "Elon Musk", User.Colors.RED),
            new User(5, "pashka", "Pavel Mavrin", User.Colors.GREEN),
            new User(7, "geranazavr555", "Georgiy Nazarov", User.Colors.RED),
            new User(11, "cannon147", "Erofey Bashunov", User.Colors.BLUE)
    );

    private static final List<Point> POINTS = Arrays.asList(
            new Point("/index", "Index"),
            new Point("/users", "Users"),
            new Point("/misc/help", "Help")
            );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Hello World", "Programming on JAVA is great!", 1),
            new Post(2, "War and Peace",  "There is no hero and no heroine. This is the story of a group of people living within a society. Andrei Bolkonsky is not Tolstoy’s hero, and Natasha is not a romantic heroine. It forgives ideas of heroism, most beautifully in the last words any character speaks in the book, as Andrei’s son thinks of his father at the end of the First Epilogue. It understands and sympathises with those ideas but it excuses itself from repeating them. The book will try to understand why people behave as they do, and it may make the best case possible for some strange actions, but it won’t make apologies for anyone and won’t pass a final judgment. Don’t expect to be able to predict what happens. Even the characters won’t be able to explain why they do what they do, perhaps until weeks or months later. The subject of the book is the wildness of possibility, and how the world can be changed by one woman saying, for no particular reason that she can explain, I have had so little happiness in my life.", 3),
            new Post(69, "Kek", "Postav`te zachet pozhaluista", 1),
            new Post(77, "Tak bit` ne dolzhno", "Shalito", 2),
            new Post(778, "Easy-pizi", "Zametki o motivacii", 1)
            );

    private static List<User> getUsers() {
        return USERS;
    }

    private static List<Point> getPoints() {
        return POINTS;
    }

    private static List<Post> getPosts(){return POSTS;}

    public static void putData(Map<String, Object> data) {
        data.put("users", getUsers());
        data.put("points", getPoints());
        data.put("posts", getPosts());
        for (Point point : getPoints()) {
            if (point.getLink().equals(data.get("URI"))) {
                data.put("currentTopic", point);
            }
        }
        for (User user : getUsers()) {
            if (data.get("logged_user_id") != null && user.getId() == (long) data.get("logged_user_id")) {
                data.put("user", user);
            }
        }
    }
}
