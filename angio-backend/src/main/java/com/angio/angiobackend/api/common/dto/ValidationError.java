package com.angio.angiobackend.api.common.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ValidationError extends Error {

    private final Map<String, String> validationResult;

    private ValidationError(String message, Map<String, String> validationResult) {
        super(message);

        this.validationResult = validationResult;
    }

    public static ValidationError of(String message, Map<String, String> validationResult) {
        return new ValidationError(message, validationResult);
    }
}
