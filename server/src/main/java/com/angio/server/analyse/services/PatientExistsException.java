package com.angio.server.analyse.services;

public class PatientExistsException extends Exception {
    public PatientExistsException(String message) {
        super(message);
    }

    public PatientExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}