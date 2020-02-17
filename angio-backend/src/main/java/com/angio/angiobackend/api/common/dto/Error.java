package com.angio.angiobackend.api.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {

    private final String message;

    public static Error of(String message) {
        return new Error(message);
    }
}
