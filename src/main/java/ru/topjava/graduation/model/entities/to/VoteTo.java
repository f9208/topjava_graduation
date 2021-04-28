//package ru.topjava.graduation.model.entities.to;
//
//import ru.topjava.graduation.model.entities.AbstractBaseEntity;
//import ru.topjava.graduation.model.entities.Vote;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class VoteTo extends AbstractBaseEntity {
//    int restaurant_id;
//    int user_id;
//    LocalDateTime localDateTime;
//
//    public VoteTo(Vote vote) {
//        super(vote.getId());
//        restaurant_id = vote.getRestaurantId();
//        user_id = vote.getUserId();
//        this.localDateTime = vote.getDate();
//    }
//
//    public static List<VoteTo> getListVoteTo(List<Vote> votes) {
//        //todo переделать на стрим
//        List<VoteTo> result = new ArrayList<>();
//        for (Vote one : votes
//        ) {
//            result.add(new VoteTo(one));
//        }
//        return result;
//    }
//
//    public LocalDateTime getLocalDateTime() {
//        return localDateTime;
//    }
//
//    public int getRestaurant_id() {
//        return restaurant_id;
//    }
//
//    public int getUser_id() {
//        return user_id;
//    }
//
//    public void setRestaurant_id(int restaurant_id) {
//        this.restaurant_id = restaurant_id;
//    }
//
//    public void setUser_id(int user_id) {
//        this.user_id = user_id;
//    }
//
//    public void setLocalDateTime(LocalDateTime localDateTime) {
//        this.localDateTime = localDateTime;
//    }
//}
