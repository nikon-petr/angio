package com.angio.angiobackend.api.notification.service.impl;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.notification.entity.Notification;
import com.angio.angiobackend.api.notification.repository.NotificationRepository;
import com.angio.angiobackend.api.notification.dto.AbstractNotification;
import com.angio.angiobackend.api.notification.service.PushNotificationService;
import com.angio.angiobackend.api.notification.type.NotificationType;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import com.angio.angiobackend.api.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.utils.collections.ConcurrentHashSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@AllArgsConstructor
@Service
public class PushNotificationServiceImpl implements PushNotificationService<UUID> {

    private final Map<UUID, Set<DeferredResult<AbstractNotification<UUID>>>> deferredResultPool = new ConcurrentHashMap<>();

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final DynamicLocaleMessageSourceAccessor msa;

    /**
     * Send push-notification to user with given id.
     *
     * @param id user id
     * @param payload push-notification payload
     * @param tag notification tag, e.g. name of updated domain entity
     */
    @Override
    @Transactional
    public void notifyUser(@NonNull UUID id, @NonNull String payload, @NonNull String tag) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(
                        msa.getMessage("errors.api.user.userWithIdNotFound", new Object[] {id})));

        log.debug("notifyUser() - start: id={}, notification={}", id, payload);
        Notification notification = new Notification()
                .setType(NotificationType.PUSH)
                .setDate(new Date())
                .setPayload(payload)
                .setRead(false)
                .setTag(tag)
                .setUser(user);

        log.debug("notifyUser() - send notification");
        notification.setRead(sendNotification(user.getId(), notification));

        log.debug("notifyUser() - save notification to db");
        notificationRepository.save(notification);

        log.debug("notifyUser() - end");
    }

    /**
     * Send push-notification to users with given ids.
     *
     * @param ids users ids
     * @param payload push-notification payload
     * @param tag notification tag, e.g. name of updated domain entity
     */
    @Override
    @Transactional
    public void notifyUsers(@NonNull Collection<UUID> ids, @NonNull String payload, @NonNull String tag) {

        List<User> users = userRepository.findAllById(ids);
        List<Notification> notifications = new ArrayList<>();

        log.debug("notifyUsers() - start: ids={}, notification={}", ids, payload);
        for (User user : users) {

            Notification notification = new Notification()
                    .setType(NotificationType.PUSH)
                    .setDate(new Date())
                    .setPayload(payload)
                    .setRead(false)
                    .setTag(tag)
                    .setUser(user);

            log.debug("notifyUsers() - send notification");
            notification.setRead(sendNotification(user.getId(), notification));

            notifications.add(notification);
        }

        log.debug("notifyUsers() - save notification to db");
        notificationRepository.saveAll(notifications);

        log.debug("notifyUsers() - end");
    }

    /**
     * Send push-notification to all users.
     *
     * @param payload push-notification payload
     * @param tag notification tag, e.g. name of updated domain entity
     */
    @Override
    @Transactional
    public void notifyAllUsers(@NonNull String payload, @NonNull String tag) {

        log.debug("notifyAllUsers() - start: message={}", payload);
        notifyUsers(deferredResultPool.keySet(), payload, tag);

        log.debug("notifyAllUsers() - end");
    }

    /**
     * Add new {@link DeferredResult<AbstractNotification<UUID>} to pool of pending responses.
     *
     * @param id user id
     * @param newResponse pending response
     */
    @Override
    public void addResponse(@NonNull UUID id, @NonNull DeferredResult<AbstractNotification<UUID>> newResponse) {

        log.debug("addResponse() - request to add new connection for user id={}", id);
        newResponse.onCompletion(() -> removeUnusableResponse(id, newResponse));

        log.debug("addResponse() - add new connection");
        deferredResultPool.putIfAbsent(id, new ConcurrentHashSet<>());
        deferredResultPool.get(id).add(newResponse);

        log.debug("addResponse() - end");
    }

    private void removeUnusableResponse(UUID id, DeferredResult<AbstractNotification<UUID>> response) {
        log.debug("removeUnusableResponse() - start: id={}", id);
        Set<DeferredResult<AbstractNotification<UUID>>> deferredResultsByUser = deferredResultPool.getOrDefault(id, null);

        if (deferredResultsByUser != null) {
            deferredResultsByUser.remove(response);
        }
        log.debug("removeUnusableResponse() - end");
    }

    private boolean sendNotification(UUID id, AbstractNotification<UUID> notification) {
        Set<DeferredResult<AbstractNotification<UUID>>> deferredResults = deferredResultPool.getOrDefault(id, null);

        if (deferredResults == null || deferredResults.isEmpty()) {
            return false;
        }

        log.debug("notifyUser() - notify by connections={}", deferredResults);

        for (DeferredResult<AbstractNotification<UUID>> deferredResult : deferredResults) {
            deferredResult.setResult(NotificationImpl.of(notification));
        }

        return true;
    }

    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    private static class NotificationImpl implements AbstractNotification<UUID> {

        private UUID id;
        private Date date;
        private String payload;
        private Boolean read;
        private String tag;

        public static AbstractNotification<UUID> of(AbstractNotification<UUID> notification) {
            return new NotificationImpl()
                    .setId(notification.getId())
                    .setDate(notification.getDate())
                    .setPayload(notification.getPayload())
                    .setRead(notification.getRead())
                    .setTag(notification.getTag());
        }
    }
}
