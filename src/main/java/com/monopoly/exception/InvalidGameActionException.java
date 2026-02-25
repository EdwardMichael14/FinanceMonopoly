package com.monopoly.exception;

public class InvalidGameActionException extends MonopolyGameException{
    public InvalidGameActionException(String message) {
        super(message);
    }
}
