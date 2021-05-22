package ru.f9208.choiserestaurant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.f9208.choiserestaurant.model.entities.Vote;
import ru.f9208.choiserestaurant.web.Exceptions.TooLateVoteException;
import ru.f9208.choiserestaurant.model.entities.to.VoteResults;
import ru.f9208.choiserestaurant.model.entities.to.VoteTo;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;
import ru.f9208.choiserestaurant.repository.VoteRepository;
import ru.f9208.choiserestaurant.utils.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.f9208.choiserestaurant.utils.DateTimeUtils.TOO_LATE;

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
    ResponseEntity<VoteTo> toVote(@RequestBody int restaurantId) {
        if (LocalTime.now().isBefore(TOO_LATE)) {
            Vote created = voteRepository.toVote(LocalDate.now(), SecurityUtil.getAuthUserId(), restaurantId);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(VOTES + "/{vote_id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(new VoteTo(created));
        } else {
            throw new TooLateVoteException("You vote after 11:00 AM. Your voice was not counted");
        }
    }
}
