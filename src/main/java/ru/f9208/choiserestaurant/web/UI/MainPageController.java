package ru.f9208.choiserestaurant.web.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.f9208.choiserestaurant.model.entities.ImageLabel;
import ru.f9208.choiserestaurant.model.entities.Vote;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;
import ru.f9208.choiserestaurant.repository.VoteRepository;
import ru.f9208.choiserestaurant.utils.imageUtils.HandlerImage;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Controller
public class MainPageController {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    HandlerImage handlerImage;

    @GetMapping("/")
    public String welcome(Model model) {
        //закидываем ресторан с результатами сегодяншенго голосования
        model.addAttribute("restaurants", restaurantRepository.getAllWithVotesBetween(LocalDate.now(), LocalDate.now()));
        return "index";
    }

    @GetMapping("test")
    public String test(HttpServletRequest request) {
        return "test";
    }

    @PostMapping("test")
    public String testPost(@RequestParam("name") @NotNull String name,
                           @RequestParam("inputFile") MultipartFile inputFile,
                           HttpServletRequest request, Model model) throws Exception {
        if (!inputFile.isEmpty()) {
            ImageLabel savedImageLabel = handlerImage.serviceSaveInputFileImage(inputFile,
                    request.getServletContext().getRealPath(""),
                    name);
            model.addAttribute("reducedImagePath", savedImageLabel.getLinkReduced());
        }
        return "test";
    }
}