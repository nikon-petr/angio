package com.angio.angiobackend.api.notification.service.impl;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service("emailNotificationService")
@Profile("prod")
public class EmailNotificationService implements NotificationService<UUID> {

    protected final JavaMailSender emailSender;
    protected final Configuration freeMarkerConfig;
    protected final UserRepository userRepository;
    protected final AngioBackendProperties props;
    protected final DynamicLocaleMessageSourceAccessor msa;

    @Async
    @Override
    public void notifyUser(@NonNull UUID id, @NonNull AbstractNotification notification) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotificationException(
                        msa.getMessage("errors.api.user.userWithIdNotFound", new Object[] {id})));

        log.debug("notifyUser() - start: id={}, notification={}", id, notification);

        log.debug("notifyUser() - send notification");
        sendEmail(user.getEmail(), notification);

        log.debug("notifyUser() - end");
    }

    @Async
    @Override
    public void notifyUsers(@NonNull Collection<UUID> ids, @NonNull AbstractNotification notification) {
        log.debug("notifyUsers() - start: ids={}, notification={}", ids, notification);
        List<User> users = userRepository.findAllById(ids);

        for (User user : users) {
            log.debug("notifyUsers() - send notification");
            sendEmail(user.getEmail(), notification);
        }

        log.debug("notifyUsers() - end");
    }

    @Async
    @Override
    public void notifyAllUsers(@NonNull AbstractNotification notification) {

        log.debug("notifyAllUsers() - start: notification={}", notification);
        List<User> users = userRepository.findAll();

        for (User user : users) {
            log.debug("notifyUsers() - send notification");
            sendEmail(user.getEmail(), notification);
        }

        log.debug("notifyUsers() - end");
    }

    protected void sendEmail(String email,  AbstractNotification notification) {

        log.debug("sendEmail() - start, email: {}, subject: {}", email, notification.getSubject());

        String body = processEmailNotificationBody(notification.getDataModel(), notification.getTemplateName());

        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setFrom("angiovision.team@mail.ru");
            mimeMessageHelper.setSubject(notification.getSubject().getName());
            mimeMessageHelper.setText(body, true);
            emailSender.send(mimeMessage);
            saveEmailToFs(mimeMessage);
        } catch (MessagingException e) {
            log.error("Notification error", e);
            throw new NotificationException(
                    msa.getMessage("errors.api.notification.sendingEmailFailed", new Object[] {email}));
        }
    }

    protected void saveEmailToFs(MimeMessage mimeMessage) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String date = df.format(new Date());

            String filePath = String.format("%s/%s/%s", props.getSentEmailDirectory(), date, FileUtils.generateHashedNameForFile("eml"));

            File emailFile = new File(filePath);
            emailFile.getParentFile().mkdirs();
            filePath = emailFile.getAbsolutePath();
            log.debug("saveEmailToFs() - save to file={}", filePath);

            OutputStream out = new FileOutputStream(filePath);
            mimeMessage.writeTo(out);

        } catch (MessagingException | IOException e) {
            log.error("Notification error", e);
        }
    }

    protected String processEmailNotificationBody(Object dataModel, String templateName) {
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
