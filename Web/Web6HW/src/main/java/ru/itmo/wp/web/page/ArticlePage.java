package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class ArticlePage extends Page{
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {

        // No operations.
    }


    private void createArticle(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        User user = (User) request.getSession().getAttribute("user");
        articleService.createArticle(user, title, text);
        request.getSession().setAttribute("message", "You are successfully create title!");
        throw new RedirectException("/article");
    }

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view){
        super.before(request, view);
        if (request.getSession().getAttribute("user") == null){
            super.setMessage("You must log in to write Articles");
            throw new RedirectException("/index");
        }
    }
}
