package com.angio.server.security.services;

public class IncorrectPasswordException extends Exception {

    public IncorrectPasswordException(String s) {
        super(s);
    }

    public IncorrectPasswordException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
