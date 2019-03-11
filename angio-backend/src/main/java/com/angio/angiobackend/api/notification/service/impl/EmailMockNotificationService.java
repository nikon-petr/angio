package com.angio.angiobackend.api.notification.service.impl;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import com.angio.angiobackend.api.notification.dto.AbstractNotification;
import com.angio.angiobackend.api.notification.entity.PushNotification;
import com.angio.angiobackend.api.notification.repository.NotificationRepository;
import com.angio.angiobackend.api.notification.service.NotificationService;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service("emailNotificationService")
@Profile("!prod")
public class EmailMockNotificationService implements NotificationService<UUID> {

    private final Configuration freeMarkerConfig;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final DynamicLocaleMessageSourceAccessor msa;

    @Override
    public void notifyUser(@NonNull UUID id, @NonNull AbstractNotification notification)
            throws IOException, TemplateException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        msa.getMessage("errors.api.user.userWithIdNotFound", new Object[] {id})));

        log.debug("notifyUser() - start: id={}, notification={}", id, notification);
        String notificationBody = processEmailNotificationBody(notification.getDataModel(), notification.getTemplateName());

        log.debug("notifyUser() - send notification");
        sendNotification(user.getEmail(), notification);

        log.debug("notifyUser() - end");
    }

    @Override
    public void notifyUsers(@NonNull Collection<UUID> ids, @NonNull AbstractNotification notification)
            throws IOException, TemplateException {

        log.debug("notifyUsers() - start: ids={}, notification={}", ids, notification);
        List<User> users = userRepository.findAllById(ids);
        List<PushNotification> notifications = new ArrayList<>();

        for (User user : users) {

            String notificationBody = processEmailNotificationBody(notification.getDataModel(), notification.getTemplateName());

            log.debug("notifyUsers() - send notification");
            sendNotification(user.getEmail(), notification);
        }

        log.debug("notifyUsers() - save notification to db");
        notificationRepository.saveAll(notifications);

        log.debug("notifyUsers() - end");
    }

    @Override
    public void notifyAllUsers(@NonNull AbstractNotification notification) throws IOException, TemplateException {

        log.debug("notifyAllUsers() - start: notification={}", notification);
        List<User> users = userRepository.findAll();

        List<PushNotification> notifications = new ArrayList<>();

        for (User user : users) {

            String notificationBody = processEmailNotificationBody(notification.getDataModel(), notification.getTemplateName());

            log.debug("notifyUsers() - send notification");
            sendNotification(user.getEmail(), notification);

        }

        log.debug("notifyUsers() - save notification to db");
        notificationRepository.saveAll(notifications);

        log.debug("notifyUsers() - end");
    }

    private boolean sendNotification(String email, AbstractNotification notification) {
        log.info("New email notification\nAddress: {}\nTemplate: {}\nSubject: {}\nData: {}",
                email,
                notification.getTemplateName(),
                notification.getSubject().getName(),
                notification.getDataModel());
        return true;
    }

    private String processEmailNotificationBody(Object dataModel, String templateName) throws IOException,
            TemplateException {
        Template template = freeMarkerConfig.getTemplate("email/root.ftl");
        Map<String, Object> root = new HashMap<>();
        root.put("data", dataModel);
        root.put("contentTemplate", templateName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
    }
}
