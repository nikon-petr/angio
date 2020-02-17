package com.angio.angiobackend.api.notification.service;

import com.angio.angiobackend.api.notification.dto.AbstractNotification;
import freemarker.template.TemplateException;
import lombok.NonNull;

import java.io.IOException;
import java.util.Collection;

/**
 * Abstract notification service.
 *
 * @param <ID> type of user entity id
 */
public interface NotificationService<ID> {

    void notifyUser(@NonNull ID id, @NonNull AbstractNotification notification);

    void notifyUsers(@NonNull Collection<ID> notifications, @NonNull AbstractNotification notification);

    void notifyAllUsers(@NonNull AbstractNotification notification);
}
