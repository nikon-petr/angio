package com.angio.angiobackend.api.security.exception;

public class IncorrectPasswordException extends Exception {

    public IncorrectPasswordException(String s) {
        super(s);
    }

    public IncorrectPasswordException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
