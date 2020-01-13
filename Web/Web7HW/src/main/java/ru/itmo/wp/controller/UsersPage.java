package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/switchDisabled")
    public String switchDisabled(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_ID_SESSION_KEY) != null) {
            try {
                long id = Long.parseLong(request.getParameter("id"));
                String status = request.getParameter("status");
                String disabled = status.equalsIgnoreCase("enable") ? "false" : "true";
                userService.switchDisabled(id, disabled);
            } catch (NumberFormatException ignored) {
                putMessage(request.getSession(), "Invalid user id");
            }
        } else {
            putMessage(request.getSession(), "Must log to ban someone");
        }
        return "redirect:/users/all";
    }
}
