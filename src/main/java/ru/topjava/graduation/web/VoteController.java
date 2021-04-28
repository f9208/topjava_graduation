package ru.topjava.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.graduation.Exceptions.TooLateVoteException;
import ru.topjava.graduation.model.entities.Vote;
import ru.topjava.graduation.repository.VoteRepository;
import ru.topjava.graduation.utils.DateTimeUtils;
import ru.topjava.graduation.utils.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String PATH = "/votes";
    @Autowired
    VoteRepository voteRepository;

    @GetMapping
    public List<Vote> getMyVotes() {
        return voteRepository.getAllForUser(SecurityUtil.getAuthUser().getId());
    }

    @GetMapping("/{id}")
    public Vote getOne(@PathVariable("id") Integer id) {
        return voteRepository.get(id);
    }

    //надо ли вообще такой обработчик
    @GetMapping("/today")
    public List<Vote> getForToday() {
        //todo сделать рефакторинг
        return voteRepository.getAllForUserBetween(LocalDate.now(), LocalDate.now(), SecurityUtil.getAuthUser().getId());
    }

    @GetMapping(value = "/filter")
    public List<Vote> getMyVotesFilter(@RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate start,
                                       @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate end) {
        if (start == null) start = DateTimeUtils.MIN_DATE;
        if (end == null) end = DateTimeUtils.MAX_DATE;
        return voteRepository.getAllForUserBetween(start, end, SecurityUtil.getAuthUser().getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Vote> toVote(@RequestBody Integer restaurantId, BindingResult result) {
        if (result.hasErrors()) {
            // TODO change to exception handler для невалидного ресторана, например
        }
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isBefore(LocalTime.of(23, 30, 00))) {
            Vote created = voteRepository.toVote(LocalDate.now(), SecurityUtil.getAuthUser().getId(), restaurantId);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(PATH + "/{vote_id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        } else {
            throw new TooLateVoteException();
        }
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteVote(@RequestBody int id) {
        voteRepository.deleteVoteForUser(id, SecurityUtil.getAuthUser().getId());
    }
}
