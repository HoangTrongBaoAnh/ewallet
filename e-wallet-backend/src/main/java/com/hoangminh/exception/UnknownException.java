package com.hoangminh.exception;

@SuppressWarnings("serial")
public class UnknownException extends RuntimeException{

    public UnknownException(String message) {
        super(message);
    }
}
