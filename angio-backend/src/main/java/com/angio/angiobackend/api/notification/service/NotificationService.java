package com.angio.angiobackend.api.notification.service;

import com.angio.angiobackend.api.notification.dto.AbstractNotification;
import com.angio.angiobackend.api.notification.dto.NewNotificationDto;
import com.angio.angiobackend.api.notification.dto.SubjectDto;
import com.angio.angiobackend.api.notification.dto.SystemNotificationDto;
import com.angio.angiobackend.api.notification.type.NotificationType;
import lombok.NonNull;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * Abstract notification service.
 *
 * @param <ID> type of user entity id
 */
public interface NotificationService<ID> {

    UUID ROOT_USER_ID = UUID.fromString("c28e0d38-410b-11e9-b28d-0242ac130002");

    void notifyUser(@NonNull ID id, @NonNull AbstractNotification notification);

    void notifyUsers(@NonNull Collection<ID> notifications, @NonNull AbstractNotification notification);

    void notifyAllUsers(@NonNull AbstractNotification notification);

    default void systemNotification(@NonNull NotificationType type, @NonNull String text) {
        NewNotificationDto notification = new NewNotificationDto()
                .setDate(new Date())
                .setType(type)
                .setTemplateName("system-notification.ftl")
                .setSubject(new SubjectDto("Уведомление о событии"))
                .setDataModel(new SystemNotificationDto()
                        .setDate(new Date())
                        .setType(type)
                        .setText(text));
        notifyUser((ID) ROOT_USER_ID, notification);
    }
}
