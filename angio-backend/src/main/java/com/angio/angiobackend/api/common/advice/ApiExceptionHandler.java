package com.angio.angiobackend.api.common.advice;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.dto.Error;
import com.angio.angiobackend.api.common.dto.Response;
import com.angio.angiobackend.api.common.dto.ValidationError;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonParseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@AllArgsConstructor
@Order(2)
@RestControllerAdvice
public class ApiExceptionHandler {

    private final DynamicLocaleMessageSourceAccessor msa;

    @ExceptionHandler({ResourceNotFoundException.class, NoHandlerFoundException.class})
    public Object handleNotFoundException(Exception e, HttpServletRequest request) {
        if (request.getRequestURI().startsWith("/api/v2")) {
            log.debug("handleNotFoundException() - send 404 error");
            return new ResponseEntity(Response.error(Error.of(e.getMessage())), NOT_FOUND);
        } else {
            log.debug("handleNotFoundException() - redirect to index.html");
            return new ModelAndView("forward:/");
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(FORBIDDEN)
    public Error handleAccessDeniedException(Exception e) {
        return Error.of(msa.getMessage("errors.api.common.security.accessDenied"));
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

        return ValidationError.of(msa.getMessage("errors.api.common.validation"), validationResult);
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(BAD_REQUEST)
    public Error handleJsonMappingException(JsonParseException e) {
        return Error.of(msa.getMessage("errors.api.common.json.invalid"));
    }

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(BAD_REQUEST)
    public Error missingPathVariableException(MissingPathVariableException e) {
        return Error.of(msa.getMessage("errors.api.common.pathVariable.notFound", new Object[] {e.getVariableName()}));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    public Error handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String parameterTypeName = e.getParameter().getParameterType().toString().toLowerCase();
        return Error.of(msa.getMessage("errors.api.common.pathVariable.invalidType", new Object[] {e.getName(), parameterTypeName}));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(BAD_REQUEST)
    public Error missingServletRequestParameter(MissingServletRequestParameterException e) {
        return Error.of(msa.getMessage("errors.api.common.request.param.required", new Object[] {e.getParameterName()}));
    }
}
