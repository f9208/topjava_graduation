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
import ru.topjava.graduation.model.entities.to.VoteResults;
import ru.topjava.graduation.model.entities.Vote;
import ru.topjava.graduation.model.entities.to.VoteTo;
import ru.topjava.graduation.repository.RestaurantRepository;
import ru.topjava.graduation.repository.VoteRepository;
import ru.topjava.graduation.utils.DateTimeUtils;
import ru.topjava.graduation.utils.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.topjava.graduation.model.entities.to.VoteTo.convert;
import static ru.topjava.graduation.utils.VoteTimeLimit.TOO_LATE;

@RestController
@RequestMapping(value = VoteController.VOTES, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String VOTES = "/votes";
    static final String RESULTS = "/results";
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping(RESULTS)
    public List<VoteResults> voteResults(@RequestParam(name = "day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate day) {
        if (day == null) day = LocalDate.now();
        LocalDate finalDay = day;
        return restaurantRepository.getAll().stream()
                .map((r) -> new VoteResults(r.getId(), voteRepository.countResultVote(finalDay, r.getId())))
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<VoteTo> toVote(@RequestBody int restaurantId, BindingResult result) {
        if (result.hasErrors()) {
        }
        if (LocalTime.now().isBefore(TOO_LATE)) {
            Vote created = voteRepository.toVote(LocalDate.now(), SecurityUtil.getAuthUserId(), restaurantId);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(VOTES + "/{vote_id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(new VoteTo(created));
        } else {
            throw new TooLateVoteException();
        }
    }
}
