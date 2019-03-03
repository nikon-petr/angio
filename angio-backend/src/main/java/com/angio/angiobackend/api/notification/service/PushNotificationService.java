package com.angio.angiobackend.api.notification.service;

import com.angio.angiobackend.api.notification.dto.AbstractNotification;
import lombok.NonNull;
import org.springframework.web.context.request.async.DeferredResult;

public interface PushNotificationService<ID> extends NotificationService<ID> {

    void addResponse(@NonNull ID id, @NonNull DeferredResult<AbstractNotification<ID>> connection);
}
