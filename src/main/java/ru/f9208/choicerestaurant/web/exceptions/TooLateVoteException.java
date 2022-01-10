package ru.f9208.choicerestaurant.web.exceptions;

public class TooLateVoteException extends RuntimeException {
    public TooLateVoteException(String message) {
        super(message);
    }
}
