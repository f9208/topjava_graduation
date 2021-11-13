package ru.f9208.choiserestaurant.web.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.f9208.choiserestaurant.model.AuthorizedUser;
import ru.f9208.choiserestaurant.model.entities.Vote;
import ru.f9208.choiserestaurant.repository.VoteRepository;

import static ru.f9208.choiserestaurant.web.PathConstants.VOTE;

@Controller
public class VoteUiController {
    @Autowired
    VoteRepository voteRepository;

    public final static String PATH = VOTE;

    @PostMapping("/vote/restaurant/{id}")
    public String vote(@AuthenticationPrincipal AuthorizedUser user,
                       @PathVariable("id") int restaurantId) {
        int userId = user.getUserTo().getId();
        Vote v = voteRepository.getVoteByUserIdToday(userId);
        if (v != null) {
            voteRepository.reVote(v.getId(), userId, restaurantId);
        } else {
            voteRepository.toVote(userId, restaurantId);
        }
        return "redirect:/restaurants/" + restaurantId;
    }

    @PostMapping("/vote/restaurant/delete/{id}")
    public String deleteVote(@AuthenticationPrincipal AuthorizedUser user,
                             @PathVariable("id") int restaurantId) {
        int userId = user.getUserTo().getId();
        Vote currentVote = voteRepository.getVoteByUserIdToday(userId);
        voteRepository.deleteVote(currentVote.getId());
        return "redirect:/restaurants/" + restaurantId;
    }
}
