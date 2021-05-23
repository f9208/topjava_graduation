package ru.f9208.choicerestaurant.web.exceptions;

public class IllegalRequestDataException extends RuntimeException {
    public IllegalRequestDataException(String msg) {
        super(msg);
    }
}