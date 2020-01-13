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
public class LogoutPage extends Page{
    private final EventRepositoryImpl eventRepository = new EventRepositoryImpl();

    private void action(HttpServletRequest request) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        eventRepository.save(Event.type.LOGOUT, user.getId());
        //eventRepository.save("LOGOUT", user.getId());
        request.getSession().removeAttribute("user");
        super.setMessage("Good bye. Hope to see you soon!");
        throw new RedirectException("/index");
    }
}
