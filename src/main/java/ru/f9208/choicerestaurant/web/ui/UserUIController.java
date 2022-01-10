package ru.f9208.choicerestaurant.web.ui;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.f9208.choicerestaurant.model.entities.to.UserTo;

@Controller
public class UserUIController {
    @GetMapping("/login")
    public String login(Model model, @RequestParam(name = "registered") @Nullable boolean newUser) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("registered", newUser);
        return "login";
    }
}
