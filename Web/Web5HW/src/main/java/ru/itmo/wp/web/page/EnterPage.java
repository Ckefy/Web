package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

/** @noinspection unused*/
public class EnterPage extends Page{
    private final UserService userService = new UserService();
    private final EventRepositoryImpl eventRepository = new EventRepositoryImpl();

    private void enter(HttpServletRequest request, Map<String, Object> view) throws ValidationException, SQLException {
        String loginOrEmail = request.getParameter("loginOrEmail");
        String password = request.getParameter("password");
        User user = userService.validateAndFindByLoginOrEmailAndPassword(loginOrEmail, password);
        eventRepository.save(Event.type.ENTER, user.getId());
        request.getSession().setAttribute("user", user);
        super.setMessage("Hello, " + user.getLogin());
        throw new RedirectException("/index");
    }
}
