package com.angio.angiobackend.api.notification.service.impl;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.notification.dto.AbstractNotification;
import com.angio.angiobackend.api.notification.dto.PushNotificationDto;
import com.angio.angiobackend.api.notification.entity.PushNotification;
import com.angio.angiobackend.api.notification.exception.NotificationException;
import com.angio.angiobackend.api.notification.mapper.PushNotificationMapper;
import com.angio.angiobackend.api.notification.repository.PushNotificationRepository;
import com.angio.angiobackend.api.notification.service.NotificationService;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service("pushNotificationService")
public class PushNotificationService implements NotificationService<UUID> {

    private final Map<UUID, Queue<DeferredResult<PushNotificationDto>>> deferredResultPool = new ConcurrentHashMap<>();

    private final Configuration freeMarkerConfig;
    private final UserRepository userRepository;
    private final TransactionTemplate transactionTemplate;
    private final PushNotificationRepository pushNotificationRepository;
    private final PushNotificationMapper pushNotificationMapper;
    private final DynamicLocaleMessageSourceAccessor msa;

    @Async
    @Override
    @Transactional
    @CacheEvict(cacheNames = "notification.list", key = "#userId")
    public void notifyUser(@NonNull UUID userId, @NonNull AbstractNotification notification) {

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        PushNotification savedNotification = transactionTemplate.execute((status) -> {

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotificationException(
                            msa.getMessage("errors.api.user.userWithIdNotFound", new Object[] {userId})));

            log.debug("notifyUser() - start: notification={}", notification);
            String notificationBody = processPushNotificationBody(notification.getDataModel(), notification.getTemplateName());
            UUID notificationId = UUID.randomUUID();
            PushNotification notificationEntity = new PushNotification()
                    .setId(notificationId)
                    .setDate(new Date())
                    .setType(notification.getType())
                    .setBody(notificationBody)
                    .setRead(false)
                    .setSubject(notification.getSubject().getName())
                    .setUser(user);

            log.debug("notifyUser() - save notification to db");
            return pushNotificationRepository.save(notificationEntity);
        });

        log.debug("notifyUser() - send notification, id={}", savedNotification.getId());
        sendNotification(
                savedNotification.getUser().getId(),
                notification,
                savedNotification.getId(),
                savedNotification.getBody(),
                savedNotification.getDate()
        );

        log.debug("notifyUser() - end");
    }

    @Async
    @Override
    @Transactional
    @CacheEvict(cacheNames = "notification.list", allEntries = true)
    public void notifyUsers(@NonNull Collection<UUID> ids, @NonNull AbstractNotification notification) {

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        List<PushNotification> savedNotification = transactionTemplate.execute((status) -> {
            log.debug("notifyUsers() - start: notification={}", notification);
            List<User> users = userRepository.findAllById(ids);

            List<PushNotification> notificationEntities = new ArrayList<>();
            String notificationBody = processPushNotificationBody(notification.getDataModel(),
                    notification.getTemplateName());

            for (User user : users) {

                Date notificationDate = new Date();
                UUID notificationId = UUID.randomUUID();
                PushNotification notificationEntity = new PushNotification()
                        .setId(notificationId)
                        .setDate(notificationDate)
                        .setType(notification.getType())
                        .setBody(notificationBody)
                        .setRead(false)
                        .setSubject(notification.getSubject().getName())
                        .setUser(user);

                notificationEntities.add(notificationEntity);
            }

            log.debug("notifyUsers() - save notification to db");
            return pushNotificationRepository.saveAll(notificationEntities);
        });

        log.debug("notifyUsers() - send notifications");
        savedNotification.forEach(n -> sendNotification(
                n.getUser().getId(),
                notification,
                n.getId(),
                n.getBody(),
                n.getDate()
        ));
        log.debug("notifyAllUsers() - end");
    }

    @Async
    @Override
    @Transactional
    @CacheEvict(cacheNames = "notification.list", allEntries = true)
    public void notifyAllUsers(@NonNull AbstractNotification notification) {

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        List<PushNotification> savedNotification = transactionTemplate.execute((status) -> {

            log.debug("notifyAllUsers() - start: notification={}", notification);
            List<User> users = userRepository.findAll();

            List<PushNotification> notificationEntities = new ArrayList<>();
            String notificationBody = processPushNotificationBody(notification.getDataModel(),
                    notification.getTemplateName());

            for (User user : users) {

                Date notificationDate = new Date();
                UUID notificationId = UUID.randomUUID();
                PushNotification notificationEntity = new PushNotification()
                        .setId(notificationId)
                        .setDate(notificationDate)
                        .setType(notification.getType())
                        .setBody(notificationBody)
                        .setRead(false)
                        .setSubject(notification.getSubject().getName())
                        .setUser(user);

                notificationEntities.add(notificationEntity);
            }

            log.debug("notifyAllUsers() - save notification to db");
            return pushNotificationRepository.saveAll(notificationEntities);
        });

        log.debug("notifyAllUsers() - send notifications");
        savedNotification.forEach(n -> sendNotification(
                n.getUser().getId(),
                notification,
                n.getId(),
                n.getBody(),
                n.getDate()
        ));
        log.debug("notifyAllUsers() - end");
    }

    /**
     * Add new {@link DeferredResult<AbstractNotification<UUID>} to pool of pending responses.
     *
     * @param id user id
     * @param newResponse pending response
     */
    public void addResponse(@NonNull UUID id, @NonNull DeferredResult<PushNotificationDto> newResponse) {

        log.debug("addResponse() - request to add new connection for user id={}", id);
        newResponse.onCompletion(() -> removeUnusableResponse(id, newResponse));

        log.debug("addResponse() - add new connection");
        deferredResultPool.putIfAbsent(id, new ConcurrentLinkedQueue<>());
        deferredResultPool.get(id).offer(newResponse);

        log.debug("addResponse() - end");
    }

    /**
     * Return all notifications for current user
     *
     * @param userId user id
     * @return notification list
     */
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "notification.list", key = "#userId")
    public List<PushNotificationDto> getPushNotifications(UUID userId) {
        return pushNotificationMapper.toDtos(pushNotificationRepository.findAllByUser(userId));
    }

    /**
     * Read notifications from given id list
     *
     * @param ids notifications id list
     */
    @Transactional
    @CacheEvict(cacheNames = "notification.list", key = "#userId")
    public void readNotifications(UUID userId, List<UUID> ids) {
        log.debug("readNotifications() - start");
        List<PushNotification> notificationsToRead = pushNotificationRepository.findAllById(ids);

        log.debug("readNotifications() - read notifications: {}",
                notificationsToRead.stream().map(PushNotification::getId).collect(Collectors.toList()));
        for (PushNotification notification : notificationsToRead) {
            notification.setRead(true);
        }

        log.debug("readNotifications() - end");
        pushNotificationRepository.saveAll(notificationsToRead);
    }

    private void removeUnusableResponse(UUID id, DeferredResult<PushNotificationDto> response) {
        log.debug("removeUnusableResponse() - start: id={}", id);
        Queue<DeferredResult<PushNotificationDto>> deferredResultsByUser = deferredResultPool.getOrDefault(id, null);

        if (deferredResultsByUser != null) {
            deferredResultsByUser.remove(response);
        }
        log.debug("removeUnusableResponse() - end");
    }

    private void sendNotification(UUID id, AbstractNotification notification, UUID notificationId, String notificationBody, Date date) {
        Queue<DeferredResult<PushNotificationDto>> deferredResults = deferredResultPool.getOrDefault(id, null);

        if (deferredResults == null || deferredResults.isEmpty()) {
            return;
        }

        log.debug("notifyUser() - notify by connections={}", deferredResults);

        DeferredResult<PushNotificationDto> deferredResult;

        while(true) {

            deferredResult = deferredResults.poll();

            if (deferredResult == null) {
                break;
            }

            PushNotificationDto pushNotificationDto = new PushNotificationDto()
                    .setId(notificationId)
                    .setDate(date)
                    .setType(notification.getType())
                    .setBody(notificationBody)
                    .setSubject(notification.getSubject())
                    .setRead(false);

            deferredResult.setResult(pushNotificationDto);
        }
    }

    private String processPushNotificationBody(Object dataModel, String templateName) {
        try {
            Template template = freeMarkerConfig.getTemplate("push/" + templateName);
            Map<String, Object> root = new HashMap<>();
            root.put("data", dataModel);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
        } catch (TemplateException e) {
            throw new NotificationException(msa.getMessage("errors.api.notification.templateProcessError", new Object[] {templateName}), e);
        } catch (IOException e) {
            throw new NotificationException(msa.getMessage("errors.api.notification.templateNotFound", new Object[] {templateName}), e);
        }
    }
}
