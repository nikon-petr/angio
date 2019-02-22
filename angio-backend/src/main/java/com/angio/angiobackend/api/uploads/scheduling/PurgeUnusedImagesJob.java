package com.angio.angiobackend.api.uploads.scheduling;

import com.angio.angiobackend.api.common.scheduling.Job;
import com.angio.angiobackend.api.uploads.service.UploadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@AllArgsConstructor
@Component
public class PurgeUnusedImagesJob implements Job {

    private final UploadService uploadService;

    /**
     * Purge unused images with config defined period.
     */
    @Override
    @Transactional
    @Scheduled(cron = "${angio.app.scheduling.purge-images-period}")
    public void execute() {
        log.trace("execute() - start");
        log.info("execute() - purge unused images");
        uploadService.purgeUnusedImages();
        log.trace("execute() - end");
    }
}
