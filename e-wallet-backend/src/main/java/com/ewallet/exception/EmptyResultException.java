package com.ewallet.exception;

@SuppressWarnings("serial")
public class EmptyResultException extends RuntimeException {

    public EmptyResultException(String message) {
        super(message);
    }
}
