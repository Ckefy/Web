package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.service.UserService;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String main(Model model, @PathVariable("id") String id) {
        long longId;
        if (id.matches("[0-9]*")) {
            longId = Long.parseLong(id);
            model.addAttribute("userData", userService.findById(longId));
        } else {
            model.addAttribute("userData", null);
        }
        return "UserPage";
    }
}