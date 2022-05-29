package com.ewallet.exception;

@SuppressWarnings("serial")
public class UnknownException extends RuntimeException{

    public UnknownException(String message) {
        super(message);
    }
}
