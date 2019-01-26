package com.angio.angiobackend.api.common.advice;

import com.angio.angiobackend.api.common.dto.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Order(3)
@RestControllerAdvice
public class RootExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public Error handleDefaultException(Exception e) {
        log.error("Internal error occurred", e);
        return Error.of(e.getMessage());
    }
}
