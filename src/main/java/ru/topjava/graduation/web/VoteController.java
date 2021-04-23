package ru.topjava.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.graduation.SecurityUtil;
import ru.topjava.graduation.model.entities.Dish;
import ru.topjava.graduation.model.entities.Vote;
import ru.topjava.graduation.model.entities.to.VoteTo;

import ru.topjava.graduation.repository.VoteRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String PATH = "/votes";
    @Autowired
    VoteRepository voteRepository;

    //todo сделать доступ только для админа
    @GetMapping
    public List<VoteTo> getAllVotesWithUserAndRestaurant() {
        //todo возможно здесь сделать трансфер обьект с только айдишником. нам не нужен весь обьект, только айдишники
        return voteRepository.getAllTo();
    }

    //todo сделать так, чтобы посмотреть можно было только свои голосования. а для админа - любые
    @GetMapping("/{id}")
    public Vote getOne(@PathVariable("id") int id) {
        Vote result = voteRepository.get(id);
        return result;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Vote> toVote(@RequestBody Vote vote) {
        Vote created = voteRepository.save(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(PATH + "/{vote_id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
