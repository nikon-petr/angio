package com.angio.angiobackend.api.uploads.scheduling;

import com.angio.angiobackend.api.common.scheduling.Job;
import com.angio.angiobackend.api.notification.service.NotificationService;
import com.angio.angiobackend.api.notification.type.NotificationType;
import com.angio.angiobackend.api.uploads.service.UploadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Slf4j
@AllArgsConstructor
@Component
public class PurgeUnusedImagesJob implements Job {

    private final UploadService uploadService;

    @Qualifier("emailNotificationService")
    private final NotificationService<UUID> emailNotificationService;

    /**
     * Purge unused images with config defined period.
     */
    @Override
    @Transactional
    @Scheduled(cron = "${angiobackend.app.scheduling.purge-images-period}")
    public void execute() {
        log.debug("execute() - start");
        log.info("execute() - purge unused images");
        int deleted = uploadService.purgeUnusedImages();
        String message = String.format("Произведена регулярная очистка неиспользуемых файлов. Очищено %s.", deleted);
        emailNotificationService.systemNotification(NotificationType.INFO, message);
        log.debug("execute() - end");
    }
}
