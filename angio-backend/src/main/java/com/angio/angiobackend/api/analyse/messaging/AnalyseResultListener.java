package com.angio.angiobackend.api.analyse.messaging;

import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;
import com.angio.angiobackend.api.analyse.service.impl.AnalyseServiceImpl;
import com.angio.angiobackend.api.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Component
public class AnalyseResultListener {

    private final AnalyseServiceImpl analyseService;

    @JmsListener(destination = "${angiobackend.app.jms.analyse-results-queue}")
    public void receiveAnalyseResult(AnalyseJmsDto result) {
        log.info("receiveAnalyseResult() - start, received: {}", result);

        log.info("receiveAnalyseResult() - save result");
        analyseService.saveExecutedAnalyse(result);

        log.info("receiveAnalyseResult() - end");
    }
}
