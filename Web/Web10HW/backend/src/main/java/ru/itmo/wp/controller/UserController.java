package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.RegisterForm;
import ru.itmo.wp.service.UserService;
import ru.itmo.wp.util.BindingResultUtils;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class UserController extends ApiController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users/authorized")
    public User findAuthorized(User user) {
        return user;
    }

    @GetMapping("users")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @PostMapping("users")
    public void register(@RequestBody @Valid RegisterForm registerForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        String login = registerForm.getLogin();
        String name = registerForm.getName();
        String password = registerForm.getPassword();
        User findUser = userService.findByLogin(login);
        if (findUser != null) {
            throw new ValidationException("Login is already in use");
        }
        User user = new User();
        user.setLogin(login);
        user.setName(name);
        userService.register(user);
        userService.updatePassword(user.getId(), password);
    }
}
