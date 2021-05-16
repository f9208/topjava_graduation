package ru.topjava.graduation.web.Exceptions;

public class TooLateVoteException extends RuntimeException{
    public TooLateVoteException(String message) {
        super(message);
    }
}
