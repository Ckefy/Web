package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Message;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TalksPage extends Page {
    private TalkService talkService = new TalkService();
    private UserService userService = new UserService();

    private void sendMessage(HttpServletRequest request, Map<String, Object> view) {
        String name = request.getParameter("name");
        String message = request.getParameter("message");
        try {
            if (message == null || message.isEmpty())
                throw new ValidationException("Empty message");
            talkService.sendMessage(((User) request.getSession(true).getAttribute("user")).getId(), name, message);
        } catch (ValidationException | RepositoryException | SQLException e) {
            view.put("error", e.getMessage());
            return;
        }
        throw new RedirectException("/talks");
    }

    private void showAllDialog(HttpServletRequest request, Map<String, Object> view) {
        try {
            List<User> users = userService.findAll();
            view.put("messages", talkService.findAllMessage(((User) request.getSession(true).getAttribute("user")).getLogin(), users));
        } catch (ValidationException | RepositoryException | SQLException e) {}
    }

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view){
        super.before(request, view);
        if (request.getSession().getAttribute("user") == null){
            super.setMessage("You must log in to talk with other people");
            throw new RedirectException("/index");
        }
        view.put("users", userService.findAll());
    }
}
