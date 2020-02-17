package com.angio.angiobackend.api.common.advice;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.dto.Error;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@AllArgsConstructor
@Order(3)
@RestControllerAdvice
public class RootExceptionHandler {

    private final DynamicLocaleMessageSourceAccessor msa;

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public Error handleDefaultException(Exception e) {
        log.error(msa.getMessage("errors.api.common.internalError"), e);
        return Error.of(e.getMessage());
    }
}
