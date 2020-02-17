package com.angio.angiobackend.api.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private Status status;
    private Date date;
    private Object data;
    private Error error;

    private Response() {}

    public static Response success(Object data) {
        return new Response()
                .setStatus(Status.SUCCESS)
                .setData(data)
                .setDate(new Date());
    }

    public static Response error(Error error) {
        return new Response()
                .setStatus(Status.ERROR)
                .setDate(new Date())
                .setError(error);
    }

    private enum Status {
        SUCCESS,
        ERROR
    }
}
