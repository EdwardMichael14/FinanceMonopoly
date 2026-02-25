package com.monopoly.exception;

public class PlayerNotFoundException extends MonopolyGameException{
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
