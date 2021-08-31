package ru.f9208.choiserestaurant.web.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.f9208.choiserestaurant.model.AuthorizedUser;
import ru.f9208.choiserestaurant.model.entities.User;
import ru.f9208.choiserestaurant.model.entities.to.UserTo;
import ru.f9208.choiserestaurant.repository.UserService;

import javax.validation.Valid;

@Controller
public class ProfileUIController {
    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal AuthorizedUser authUser,
                          @RequestParam @Nullable boolean updated) {
        model.addAttribute("userTo", authUser.getUserTo());
        model.addAttribute("updated", updated);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserTo userTo, BindingResult bindingResult,
                                @AuthenticationPrincipal AuthorizedUser userAuth, Model model) {
        if (bindingResult.hasErrors()) {
            return "profile";
        }
        userService.update(userTo, userAuth.getUserTo().getId());
        userAuth.setUserTo(userTo);
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
