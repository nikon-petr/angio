package com.angio.angiobackend.api.analyse.scheduling;

import com.angio.angiobackend.api.analyse.service.AnalyseService;
import com.angio.angiobackend.api.common.scheduling.Job;
import com.angio.angiobackend.api.notification.service.NotificationService;
import com.angio.angiobackend.api.notification.type.NotificationType;
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
public class PurgeAnalysesJob implements Job {

    private final AnalyseService analyseService;

    @Qualifier("emailNotificationService")
    private final NotificationService<UUID> emailNotificationService;

    /**
     * Purge deleted analyses with config defined period.
     */
    @Override
    @Transactional
    @Scheduled(cron = "${angiobackend.app.scheduling.purge-analyses-period}")
    public void execute() {
        log.debug("execute() - start");
        log.info("execute() - purge deleted analyses");
        int deleted = analyseService.purgeAnalysesInStatusDeleted();
        String message = String.format("Произведена регулярная очистка удаленных анализов. Очищено %s.", deleted);
        emailNotificationService.systemNotification(NotificationType.INFO, message);
        log.debug("execute() - end");
    }
}
