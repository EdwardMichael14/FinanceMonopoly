package com.monopoly.exception;

public class GameNotFoundException extends MonopolyGameException{
    public GameNotFoundException(String message) {
        super(message);
    }
}
