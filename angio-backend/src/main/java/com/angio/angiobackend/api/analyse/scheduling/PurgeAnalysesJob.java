package com.angio.angiobackend.api.analyse.scheduling;

import com.angio.angiobackend.api.analyse.service.AnalyseService;
import com.angio.angiobackend.api.common.scheduling.Job;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Component
public class PurgeAnalysesJob implements Job {

    private final AnalyseService analyseService;

    /**
     * Purge deleted analyses with config defined period.
     */
    @Override
    @Transactional
    @Scheduled(cron = "${angiobackend.app.scheduling.purge-analyses-period}")
    public void execute() {
        log.debug("execute() - start");
        log.info("execute() - purge deleted analyses");
        analyseService.purgeAnalysesInStatusDeleted();
        log.debug("execute() - end");
    }
}
