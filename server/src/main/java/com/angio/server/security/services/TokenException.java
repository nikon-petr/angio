package com.angio.server.security.services;

public class TokenException extends RuntimeException {

    public TokenException(String s) {
        super(s);
    }

    public TokenException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
