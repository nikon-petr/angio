package com.angio.angiobackend.api.notification.service.impl;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.notification.dto.AbstractNotification;
import com.angio.angiobackend.api.notification.entity.Notification;
import com.angio.angiobackend.api.notification.repository.NotificationRepository;
import com.angio.angiobackend.api.notification.service.EmailNotificationService;
import com.angio.angiobackend.api.notification.type.NotificationType;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import com.angio.angiobackend.api.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
@Profile("default")
public class EmailMockNotificationServiceImpl implements EmailNotificationService<UUID> {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final DynamicLocaleMessageSourceAccessor msa;

    @Override
    public void notifyUser(@NonNull UUID id, @NonNull String payload, @NonNull String tag) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(
                        msa.getMessage("errors.api.user.userWithIdNotFound", new Object[] {id})));

        log.debug("notifyUser() - start: id={}, notification={}", id, payload);
        Notification notification = new Notification()
                .setType(NotificationType.EMAIL)
                .setDate(new Date())
                .setPayload(payload)
                .setRead(false)
                .setTag(tag)
                .setUser(user);

        log.debug("notifyUser() - send notification");
        notification.setRead(sendNotification(user.getEmail(), notification));

        log.debug("notifyUser() - save notification to db");
        notificationRepository.save(notification);

        log.debug("notifyUser() - end");
    }

    @Override
    public void notifyUsers(@NonNull Collection<UUID> ids, @NonNull String payload, @NonNull String tag) {

        log.debug("notifyUsers() - start: ids={}, notification={}", ids, payload);
        List<User> users = userRepository.findAllById(ids);
        List<Notification> notifications = new ArrayList<>();

        for (User user : users) {

            Notification notification = new Notification()
                    .setType(NotificationType.EMAIL)
                    .setDate(new Date())
                    .setPayload(payload)
                    .setRead(false)
                    .setTag(tag)
                    .setUser(user);

            log.debug("notifyUsers() - send notification");
            notification.setRead(sendNotification(user.getEmail(), notification));

            notifications.add(notification);
        }

        log.debug("notifyUsers() - save notification to db");
        notificationRepository.saveAll(notifications);

        log.debug("notifyUsers() - end");
    }

    @Override
    public void notifyAllUsers(@NonNull String payload, @NonNull String tag) {

        log.debug("notifyAllUsers() - start: message={}", payload);
        List<User> users = userRepository.findAll();

        List<Notification> notifications = new ArrayList<>();

        for (User user : users) {

            Notification notification = new Notification()
                    .setType(NotificationType.EMAIL)
                    .setDate(new Date())
                    .setPayload(payload)
                    .setRead(false)
                    .setTag(tag)
                    .setUser(user);

            log.debug("notifyUsers() - send notification");
            notification.setRead(sendNotification(user.getEmail(), notification));

            notifications.add(notification);
        }

        log.debug("notifyUsers() - save notification to db");
        notificationRepository.saveAll(notifications);

        log.debug("notifyUsers() - end");
    }

    private boolean sendNotification(String email, AbstractNotification<UUID> notification) {
        log.info("New email notification\nAddress: {}\nTheme: {}\nBody: {}",
                email,
                notification.getTag(),
                notification.getPayload());
        return true;
    }
}
