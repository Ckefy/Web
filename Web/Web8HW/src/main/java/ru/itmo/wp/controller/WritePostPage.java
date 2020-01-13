package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.TagService;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Controller
public class WritePostPage extends Page {
    private final UserService userService;

    private final TagService tagService;

    public WritePostPage(UserService userService, TagService tagService) {
        this.userService = userService;
        this.tagService = tagService;
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
        Post newPost = new Post();
        model.addAttribute("post", newPost);
        model.addAttribute("strTag", new strTag());
        return "WritePostPage";
    }

    public class strTag{
        @Size(min = 1, max = 255)
        @Pattern(regexp = "\\s*([a-z]+\\s*)+\\s*", message = "each tag must contain only lowercase latin letters")
        private String writtenTags;

        public String getWrittenTags() {
            return writtenTags;
        }

        public void setWrittenTags(String writtenTags) {
            this.writtenTags = writtenTags;
        }
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/writePost")
    public String writePostPost(@Valid @ModelAttribute("post") Post post,
                                BindingResult bindingResult,
                                @Valid @ModelAttribute("strTag") strTag writtenTags,
                                BindingResult bindingResult2,
                                HttpSession httpSession) {
        if (bindingResult.hasErrors() || bindingResult2.hasErrors()) {
            return "WritePostPage";
        }
        String[] parsedTags = writtenTags.getWrittenTags().trim().split("\\s+");
        for (String tag : parsedTags) {
            Tag curTag = tagService.getTag(tag);
            post.addTag(curTag);
        }
        userService.writePost(getUser(httpSession), post);
        putMessage(httpSession, "You published new post");

        return "redirect:/posts";
    }
}
