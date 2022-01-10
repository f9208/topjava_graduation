package ru.f9208.choicerestaurant.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.f9208.choicerestaurant.model.entities.Vote;
import ru.f9208.choicerestaurant.model.entities.to.VoteTo;
import ru.f9208.choicerestaurant.repository.VoteRepository;
import ru.f9208.choicerestaurant.utils.SecurityUtil;
import ru.f9208.choicerestaurant.web.exceptions.TooLateVoteException;

import java.net.URI;
import java.time.LocalTime;

import static ru.f9208.choicerestaurant.utils.DateTimeUtils.TOO_LATE;

@RestController
@RequestMapping(value = VoteRestController.VOTES, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    public static final String VOTES = "/votes";
    @Autowired
    private VoteRepository voteRepository;

    @GetMapping("/{vote_id}")
    public VoteTo getVoteTo(@PathVariable(name = "vote_id") int id) {
        return new VoteTo(voteRepository.getVoteByIdAndUserId(id, SecurityUtil.getAuthUserId()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VoteTo> toVote(@RequestBody int restaurantId) {
        Vote created = voteRepository.toVote(SecurityUtil.getAuthUserId(), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VOTES + "/{vote_id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(new VoteTo(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reVote(@PathVariable(name = "id") int voteId, @RequestBody int restaurantId) {
        if (LocalTime.now().isBefore(TOO_LATE)) {
            voteRepository.reVote(voteId, SecurityUtil.getAuthUserId(), restaurantId);
        } else {
            throw new TooLateVoteException("You re-vote after 11:00 AM. Your new voice was not counted, left previous one ");
        }
    }
}