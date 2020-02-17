package com.angio.angiobackend.api.common.report.exception;

public class ReportException extends RuntimeException {

    public ReportException() {
        super();
    }

    public ReportException(String message) {
        super(message);
    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
