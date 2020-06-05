package com.angio.angiobackend.api.analyse.messaging.impl;

import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;
import com.angio.angiobackend.api.analyse.messaging.AnalyseExecutorListener;
import com.angio.angiobackend.api.analyse.service.AnalyseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
@Profile("!disablejms")
public class AnalyseExecutorListenerImpl implements AnalyseExecutorListener {

    private final AnalyseService analyseService;

    @Override
    @JmsListener(destination = "${angiobackend.app.jms.analyse-execute-response}")
    public void handleExecutedAnalyse(AnalyseJmsDto result) {
        log.info("handleExecutedAnalyse() - start, received: {}", result);

        log.info("handleExecutedAnalyse() - save result");
        analyseService.saveExecutedAnalyse(result);

        log.info("handleExecutedAnalyse() - end");
    }
}
