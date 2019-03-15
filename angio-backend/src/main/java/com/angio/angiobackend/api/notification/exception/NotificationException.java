package com.angio.angiobackend.api.notification.exception;

public class NotificationException extends RuntimeException {

    public NotificationException() {
        super();
    }

    public NotificationException(String message) {
        super(message);
    }

    public NotificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
