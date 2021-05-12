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

import static ru.topjava.graduation.utils.VoteTimeLimit.TOO_LATE;

@RestController
@RequestMapping(value = VoteController.VOTES, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String VOTES = "/votes";
    @Autowired
    VoteRepository voteRepository;

    @GetMapping
    public List<Vote> getMyVotes() {
        return voteRepository.getAllForUser(SecurityUtil.getAuthUserId());
    }

    @GetMapping("/{id}")
    public Vote getOne(@PathVariable("id") Integer id) {
        return voteRepository.getOneForUser(id, SecurityUtil.getAuthUserId());
    }

    @GetMapping("/today")
    public List<Vote> getForToday() {
        //todo сделать рефакторинг
        return voteRepository.getAllForUserBetween(LocalDate.now(), LocalDate.now(), SecurityUtil.getAuthUserId());
    }

    @GetMapping(value = "/filter")
    public List<Vote> getMyVotesFilter(@RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate start,
                                       @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate end) {
        if (start == null) start = DateTimeUtils.MIN_DATE;
        if (end == null) end = DateTimeUtils.MAX_DATE;
        return voteRepository.getAllForUserBetween(start, end, SecurityUtil.getAuthUserId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Vote> toVote(@RequestBody int restaurantId, BindingResult result) {
        if (result.hasErrors()) {
            // TODO change to exception handler для невалидного ресторана, например
        }
        if (LocalTime.now().isBefore(TOO_LATE)) {
            Vote created = voteRepository.toVote(LocalDate.now(), SecurityUtil.getAuthUserId(), restaurantId);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(VOTES + "/{vote_id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        } else {
            throw new TooLateVoteException();
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteVote(@RequestBody int id) {
        voteRepository.deleteVoteForUser(id, SecurityUtil.getAuthUserId());
    }
}
