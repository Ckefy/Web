package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private UserService userService = new UserService();
    private HttpServletRequest req;

    void before(HttpServletRequest request, Map<String, Object> view) {
        req = request;
        User nowUser = getUser();
        if (nowUser != null) {
            request.getSession().removeAttribute("user");
            request.getSession().setAttribute("user", userRepository.find(nowUser.getId()));

        }
        IndexPage.findAll(req, view);
    }

    protected User getUser(){
        return (User) req.getSession().getAttribute("user");
    }

    protected void setUser(Map<String, Object> view){
        User user = getUser();
        view.put("user", user);
    }

    private void getMessage(Map<String, Object> view) {
        String message = (String) req.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            req.getSession().removeAttribute("message");
        }
    }

    protected void setMessage(String message){
        req.getSession().setAttribute("message", message);
    }

    void after(HttpServletRequest request, Map<String, Object> view) {
        setUser(view);
        getMessage(view);
    }

    private void action() {
        // No operations.
    }
}
