package com.angio.angiobackend.api.common.advice;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.dto.Error;
import com.angio.angiobackend.api.common.dto.ValidationError;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@Order(2)
@RestControllerAdvice
public class ApiExceptionHandler {

    private final DynamicLocaleMessageSourceAccessor msa;

    @ExceptionHandler({ResourceNotFoundException.class, NoHandlerFoundException.class})
    @ResponseStatus(NOT_FOUND)
    public Error handleNotFoundException(Exception e) {
        return Error.of(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ValidationError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> fieldsErrors = e.getBindingResult().getFieldErrors().stream()
                .collect(toMap(f -> format("%s.%s", f.getField(), f.getCode()),
                        DefaultMessageSourceResolvable::getDefaultMessage));
        Map<String, String> globalErrors = e.getBindingResult().getGlobalErrors().stream()
                .collect(toMap(g -> format("%s.%s", g.getObjectName(), g.getCode()),
                        DefaultMessageSourceResolvable::getDefaultMessage));

        Map<String, String> validationResult = fieldsErrors;
        validationResult.putAll(globalErrors);

        return ValidationError.of(msa.getMessage("errors.validation"), validationResult);
    }
}
