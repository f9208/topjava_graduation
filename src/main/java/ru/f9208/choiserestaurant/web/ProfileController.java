package ru.f9208.choiserestaurant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.f9208.choiserestaurant.model.entities.Role;
import ru.f9208.choiserestaurant.model.entities.User;
import ru.f9208.choiserestaurant.model.entities.to.VoteTo;
import ru.f9208.choiserestaurant.repository.UserRepository;
import ru.f9208.choiserestaurant.repository.UserService;
import ru.f9208.choiserestaurant.repository.VoteRepository;
import ru.f9208.choiserestaurant.utils.DateTimeUtils;
import ru.f9208.choiserestaurant.utils.SecurityUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static ru.f9208.choiserestaurant.model.entities.to.VoteTo.convert;
import static ru.f9208.choiserestaurant.utils.SecurityUtil.chekNotAdmin;

@RestController
@RequestMapping(value = ProfileController.PROFILE)
public class ProfileController {
    public static final String PROFILE = "/profile";
    public static final String VOTES = "/votes";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private VoteRepository voteRepository;

    @GetMapping()
    public User getProfile() {
        return userRepository.findById(SecurityUtil.getAuthUserId());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        if (chekNotAdmin(user)) user.setRoles(Set.of(Role.USER));
        User created = userService.prepareAndSave(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(PROFILE + "/{id}") //todo тоже ведет на несуществующий get
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile() {
        userRepository.delete(SecurityUtil.getAuthUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user) {
        userRepository.update(user);
    }

    @GetMapping(VOTES)
    public List<VoteTo> userVotes(@RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate start,
                                  @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate end) {
        if (start == null) start = DateTimeUtils.MIN_DATE;
        if (end == null) end = DateTimeUtils.MAX_DATE;
        return convert(voteRepository.getAllByDateBetweenAndUserId(start, end, SecurityUtil.getAuthUserId()));
    }

    @GetMapping(VOTES + "/today")
    public VoteTo getMyVoteToday() {
        return new VoteTo(voteRepository.getMyVoteForDay(LocalDate.now(), SecurityUtil.getAuthUserId()));
    }

    @GetMapping(VOTES + "/{vote_id}")
    public VoteTo getVote(@PathVariable("vote_id") int voteId) {
        return new VoteTo(voteRepository.getOneForUser(voteId, SecurityUtil.getAuthUserId()));
    }

    @DeleteMapping(value = VOTES + "/{vote_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteVote(@PathVariable("vote_id") int id) {
        voteRepository.deleteVoteForUser(id, SecurityUtil.getAuthUserId());
    }
}