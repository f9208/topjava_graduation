package ru.topjava.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.topjava.graduation.model.entities.Vote;
import ru.topjava.graduation.model.entities.to.VoteTo;
import ru.topjava.graduation.repository.VoteRepository;

import java.util.List;

@RestController
@RequestMapping(value = VoteController.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String PATH = "/votes";
    @Autowired
    VoteRepository voteRepository;

    //todo сделать доступ только для админа
    @GetMapping
    public List<VoteTo> getAllVotes() {
        //todo возможно сдесь сделать трансфер обьект с только айдишником. нам не нужен весь обьект, только айдишники
        System.out.println(voteRepository.findAll());
        return voteRepository.getAllTo();
    }

    @GetMapping("/{id}")
    public Vote getOne(@PathVariable("id") int id) {
        Vote result = voteRepository.get(id);
        System.out.println(result);
        return result;
    }
}
