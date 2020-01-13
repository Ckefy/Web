package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage extends Page{
    private final UserService userService = new UserService();
    private final UserRepository userRepository = new UserRepositoryImpl();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    public void update(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long id = Long.parseLong(request.getParameter("id"));
        User nowUser=getUser();
        if (!userRepository.find(getUser().getId()).isAdmin())
            throw new ValidationException("You are not admin");
        long type = request.getParameter("type").equals("Yes") ? 0 : 1;
        String typeS = type == 0 ? "false" : "true";
        (userService.find(id)).setAdmin(typeS);
        if (nowUser.getId()==id){
            nowUser.setAdmin(typeS);
            request.getSession().removeAttribute("user");
            request.getSession().setAttribute("user", nowUser);
        }
        userService.update(id, type);
    }

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view){
        super.before(request, view);
        findAll(request, view);
    }
}
