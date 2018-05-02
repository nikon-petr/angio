package com.angio.app.analyse.patient.services;

public class PatientExistsException extends Exception {
    public PatientExistsException(String message) {
        super(message);
    }

    public PatientExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}