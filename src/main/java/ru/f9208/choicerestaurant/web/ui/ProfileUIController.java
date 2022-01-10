package ru.f9208.choicerestaurant.web.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.f9208.choicerestaurant.model.AuthorizedUser;
import ru.f9208.choicerestaurant.model.entities.User;
import ru.f9208.choicerestaurant.model.entities.Vote;
import ru.f9208.choicerestaurant.model.entities.to.UserTo;
import ru.f9208.choicerestaurant.repository.UserService;
import ru.f9208.choicerestaurant.repository.VoteRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProfileUIController {
    @Autowired
    UserService userService;
    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal AuthorizedUser authUser,
                          @RequestParam @Nullable boolean updated) {
        model.addAttribute("userTo", authUser.getUserTo());
        model.addAttribute("updated", updated);
        List<Vote> votes = voteRepository.getAllForUser(authUser.getUserTo().getId());
        model.addAttribute("votes", votes);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserTo userTo, BindingResult bindingResult,
                                @AuthenticationPrincipal AuthorizedUser authUser, Model model) {
        if (bindingResult.hasErrors()) {
            List<Vote> votes = voteRepository.getAllForUser(authUser.getUserTo().getId());
            model.addAttribute("votes", votes);
            return "profile";
        }
        userTo.setId(authUser.getUserTo().getId());
        userService.update(userTo, authUser.getUserTo().getId());
        authUser.setUserTo(userTo);
        model.addAttribute("updated", true);
        return "redirect:/profile";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userTo", new UserTo());
        return "register";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        User u = new User(userTo);
        userService.prepareAndSave(u);
        model.addAttribute("registered", true);
        return "redirect:/login";
    }
}
