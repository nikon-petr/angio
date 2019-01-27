package com.angio.angiobackend.api.common.advice;

import com.angio.angiobackend.api.common.dto.Error;
import com.angio.angiobackend.api.common.exception.OperationException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(1)
@RestControllerAdvice
public class AngioExceptionHandler {

    @ExceptionHandler(OperationException.class)
    @ResponseStatus(BAD_REQUEST)
    public Error handleOperationException(Exception e) {
        return Error.of(e.getMessage());
    }
}
