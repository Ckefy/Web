package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage extends Page{
    private static final ArticleService articleService = new ArticleService();
    private void action(HttpServletRequest request, Map<String, Object> view) {
        putMessage(request, view);
    }

    private void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    public static void findAll(HttpServletRequest request, Map<String, Object> view) {
        if (view.get("articles") == null) view.put("articles", articleService.findAll());
    }

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view){
        super.before(request, view);
        IndexPage.findAll(request, view);
    }
}
