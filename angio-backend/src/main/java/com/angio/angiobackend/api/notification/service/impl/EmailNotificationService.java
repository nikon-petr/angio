package com.angio.angiobackend.api.notification.service.impl;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import com.angio.angiobackend.api.notification.dto.AbstractNotification;
import com.angio.angiobackend.api.notification.exception.NotificationException;
import com.angio.angiobackend.api.notification.service.NotificationService;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import com.angio.angiobackend.util.FileUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service("emailNotificationService")
@Profile("prod")
public class EmailNotificationService implements NotificationService<UUID> {

    private final JavaMailSender emailSender;
    private final Configuration freeMarkerConfig;
    private final UserRepository userRepository;
    private final AngioBackendProperties props;
    private final DynamicLocaleMessageSourceAccessor msa;

    @Override
    public void notifyUser(@NonNull UUID id, @NonNull AbstractNotification notification) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        msa.getMessage("errors.api.user.userWithIdNotFound", new Object[] {id})));

        log.debug("notifyUser() - start: id={}, notification={}", id, notification);
        String notificationBody = processEmailNotificationBody(notification.getDataModel(), notification.getTemplateName());

        log.debug("notifyUser() - send notification");
        sendNotification(user.getEmail(), notification.getSubject().getName(), notificationBody);

        log.debug("notifyUser() - end");
    }

    @Override
    public void notifyUsers(@NonNull Collection<UUID> ids, @NonNull AbstractNotification notification) {
        log.debug("notifyUsers() - start: ids={}, notification={}", ids, notification);
        List<User> users = userRepository.findAllById(ids);

        for (User user : users) {

            String notificationBody = processEmailNotificationBody(notification.getDataModel(), notification.getTemplateName());

            log.debug("notifyUsers() - send notification");
            sendNotification(user.getEmail(), notification.getSubject().getName(), notificationBody);
        }

        log.debug("notifyUsers() - end");
    }

    @Override
    public void notifyAllUsers(@NonNull AbstractNotification notification) {

        log.debug("notifyAllUsers() - start: notification={}", notification);
        List<User> users = userRepository.findAll();

        for (User user : users) {

            String notificationBody = processEmailNotificationBody(notification.getDataModel(), notification.getTemplateName());

            log.debug("notifyUsers() - send notification");
            sendNotification(user.getEmail(), notification.getSubject().getName(), notificationBody);
        }

        log.debug("notifyUsers() - end");
    }

    private void sendNotification(String email, String subject, String body) {
        log.debug("sendNotification() - start, email: {}, subject: {}", email, subject);

        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setFrom("angiovision.team@mail.ru");
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);
            emailSender.send(mimeMessage);
            saveEmailToFs(mimeMessage);
        } catch (MessagingException e) {
            log.error("Notification error", e);
            throw new NotificationException(
                    msa.getMessage("errors.api.notification.sendingEmailFailed", new Object[] {email}));
        }
    }

    private void saveEmailToFs(MimeMessage mimeMessage) {
        try {
            String fileName = new File(props.getSentEmailDirectory(), FileUtils.generateHashedNameForFile("eml")).getAbsolutePath();
            log.debug("saveEmailToFs() - save to file={}", fileName);
            OutputStream out = new FileOutputStream(fileName);
            mimeMessage.writeTo(out);
        } catch (MessagingException | IOException e) {
            log.error("Notification error", e);
        }
    }

    private String processEmailNotificationBody(Object dataModel, String templateName) {
        try {
            Template template = freeMarkerConfig.getTemplate("email/root.ftl");
            Map<String, Object> root = new HashMap<>();
            root.put("data", dataModel);
            root.put("contentTemplate", templateName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
        } catch (TemplateException e) {
            throw new NotificationException(msa.getMessage("errors.api.notification.templateProcessError", new Object[] {templateName}), e);
        } catch (IOException e) {
            throw new NotificationException(msa.getMessage("errors.api.notification.templateNotFound", new Object[] {templateName}), e);
        }
    }
}
