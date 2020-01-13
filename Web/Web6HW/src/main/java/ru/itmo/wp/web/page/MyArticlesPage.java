package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class MyArticlesPage extends Page{
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAll(((User)view.get("user")).getId()));
    }


    private void update(HttpServletRequest request, Map<String, Object> view) {
        long id = Long.parseLong(request.getParameter("id"));
        long userId = Long.parseLong(request.getParameter("userId"));
        if (userId != ((User)view.get("user")).getId()) {
            return;
        }
        long type = request.getParameter("type").equals("Show") ? 0 : 1;
        articleService.update(id, type);
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
