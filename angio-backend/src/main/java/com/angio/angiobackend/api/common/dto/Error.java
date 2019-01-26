package com.angio.angiobackend.api.common.dto;

import lombok.Data;

@Data(staticConstructor = "of")
public class Error {

    private final String message;
}
