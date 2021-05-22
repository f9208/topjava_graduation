package ru.f9208.choiserestaurant.web.Exceptions;

public class IllegalRequestDataException extends RuntimeException {
    public IllegalRequestDataException(String msg) {
        super(msg);
    }
}