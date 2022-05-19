package com.hoangminh.exception;

@SuppressWarnings("serial")
public class EmptyResultException extends RuntimeException {

    public EmptyResultException(String message) {
        super(message);
    }
}
