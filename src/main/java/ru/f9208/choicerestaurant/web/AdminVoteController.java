package ru.f9208.choicerestaurant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.f9208.choicerestaurant.model.entities.to.VoteTo;
import ru.f9208.choicerestaurant.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.f9208.choicerestaurant.model.entities.to.VoteTo.convert;

@RestController
@RequestMapping(value = AdminVoteController.ADMIN_VOTES, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {
    public static final String ADMIN_VOTES = "/admin/votes";
    @Autowired
    private VoteRepository voteRepository;

    @GetMapping("/{id}")
    public VoteTo getOne(@PathVariable("id") int id) {
        return new VoteTo(voteRepository.get(id));
    }

    @GetMapping
    public List<VoteTo> getWithParam(
            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate end,
            @RequestParam(name = "userId") @Nullable Integer userId) {
        if (start == null) start = LocalDate.now();
        if (end == null) end = LocalDate.now();
        return userId == null ? convert(voteRepository.getAllBetween(start, end)) : convert(voteRepository.getAllByDateBetweenAndUserId(start, end, userId));
    }

    @GetMapping("/today")
    public List<VoteTo> getForToday() {
        return convert(voteRepository.getAllBetween(LocalDate.now(), LocalDate.now()));
    }
}
