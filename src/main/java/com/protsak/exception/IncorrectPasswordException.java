package com.protsak.exception;

public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException(String msg) {
        super(msg);
    }
}
