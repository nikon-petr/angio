package com.angio.angiobackend.api.common.advice;

import com.angio.angiobackend.api.common.dto.Error;
import com.angio.angiobackend.api.common.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.angio.angiobackend.api")
public class ResponseBodyAdviceImpl implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Object.class.isAssignableFrom(returnType.getParameterType()) &&
                !ResponseEntity.class.isAssignableFrom(returnType.getParameterType()) &&
                !Response.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof Error) {
            log.info("beforeBodyWrite() - error: {}", body);
            return Response.error((Error) body);
        }

        return Response.success(body);
    }
}
