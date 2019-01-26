package com.angio.angiobackend.api.analyse.exception;

public class PatientExistsException extends Exception {
    public PatientExistsException(String message) {
        super(message);
    }

    public PatientExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}