package ru.f9208.choiserestaurant.web.exceptions;

public class TooLateVoteException extends RuntimeException{
    public TooLateVoteException(String message) {
        super(message);
    }
}
