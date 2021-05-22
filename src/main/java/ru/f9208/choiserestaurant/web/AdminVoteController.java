package ru.f9208.choiserestaurant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.f9208.choiserestaurant.model.entities.to.VoteTo;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;
import ru.f9208.choiserestaurant.repository.VoteRepository;
import ru.f9208.choiserestaurant.utils.DateTimeUtils;

import java.time.LocalDate;
import java.util.List;

import static ru.f9208.choiserestaurant.model.entities.to.VoteTo.convert;

@RestController
@RequestMapping(value = AdminVoteController.ADMIN_VOTES, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {
    static final String ADMIN_VOTES = "/admin/votes";
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping
    public List<VoteTo> getAll() {
        return convert(voteRepository.findAll());
    }

    @GetMapping("/{id}")
    public VoteTo getOne(@PathVariable("id") int id) {
        return new VoteTo(voteRepository.get(id));
    }

    @GetMapping("/filter")
    public List<VoteTo> getFiltered(
            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate start,
            @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate end) {
        if (start == null) start = DateTimeUtils.MIN_DATE;
        if (end == null) end = DateTimeUtils.MAX_DATE;
        return convert(voteRepository.getAllBetween(start, end));
    }

    @GetMapping("/today")
    public List<VoteTo> getForToday() {
        return convert(voteRepository.getAllBetween(LocalDate.now(), LocalDate.now()));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVote(@RequestBody int id) {
        voteRepository.deleteVote(id);
    }
}
