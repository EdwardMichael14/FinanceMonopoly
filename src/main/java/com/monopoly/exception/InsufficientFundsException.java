package com.monopoly.exception;

public class InsufficientFundsException extends MonopolyGameException{
    public InsufficientFundsException(String message) {
        super(message);
    }
}
