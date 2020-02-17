package com.angio.angiobackend.api.notification.service.impl;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.notification.dto.AbstractNotification;
import com.angio.angiobackend.api.notification.exception.NotificationException;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service("emailNotificationService")
@Profile("!prod")
public class EmailMockNotificationService extends EmailNotificationService {

    public EmailMockNotificationService(
            JavaMailSender emailSender,
            Configuration freeMarkerConfig,
            UserRepository userRepository,
            AngioBackendProperties props,
            DynamicLocaleMessageSourceAccessor msa) {
        super(emailSender, freeMarkerConfig, userRepository, props, msa);
    }

    @Override
    protected void sendEmail(String email,  AbstractNotification notification) {
        log.info("New email notification\nAddress: {}\nTemplate: {}\nSubject: {}\nData: {}",
                email,
                notification.getTemplateName(),
                notification.getSubject().getName(),
                notification.getDataModel());

        log.debug("sendEmail() - start, email: {}, subject: {}", email, notification.getSubject());
        String body = processEmailNotificationBody(notification.getDataModel(), notification.getTemplateName());

        log.debug("sendEmail() - create email and save to file system without sending");
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setFrom("angiovision.team@mail.ru");
            mimeMessageHelper.setSubject(notification.getSubject().getName());
            mimeMessageHelper.setText(body, true);
            saveEmailToFs(mimeMessage);
        } catch (MessagingException e) {
            log.error("Notification error", e);
            throw new NotificationException(
                    msa.getMessage("errors.api.notification.sendingEmailFailed", new Object[] {email}));
        }
    }
}
