package com.example.homegym.exceptions;

public class InvalidTokenException extends HomeGymException {
    public InvalidTokenException() {
        super("Invalid token");
    }
    public InvalidTokenException(String message) {
        super(message);
    }
}
