package com.angio.angiobackend.api.common.advice;

import com.angio.angiobackend.api.common.dto.Error;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(2)
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, NoHandlerFoundException.class})
    @ResponseStatus(NOT_FOUND)
    public Error handleNotFoundException(Exception e) {
        return Error.of(e.getMessage());
    }
}
