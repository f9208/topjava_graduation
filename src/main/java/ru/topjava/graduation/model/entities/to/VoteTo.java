package ru.topjava.graduation.model.entities.to;

import ru.topjava.graduation.model.entities.AbstractBaseEntity;
import ru.topjava.graduation.model.entities.Vote;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

public class VoteTo extends AbstractBaseEntity {
    int user_id;
    int restaurant_id;

    public VoteTo(Vote vote) {
        super(vote.getId());
        this.user_id = vote.getUser().getId();
        this.restaurant_id = vote.getRestaurant().getId();
    }

    //    @ConstructorProperties({"id"})
    public VoteTo(Integer id, int user_id, int restaurant_id) {
        super(id);
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
    }

    public static List<VoteTo> getListVoteTo(List<Vote> votes) {
        //todo переделать на стрим
        List<VoteTo> result = new ArrayList<>();
        for (Vote one : votes
        ) {
            result.add(new VoteTo(one));
        }
        return result;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", restaurant_id=" + restaurant_id +
                '}';
    }
}
