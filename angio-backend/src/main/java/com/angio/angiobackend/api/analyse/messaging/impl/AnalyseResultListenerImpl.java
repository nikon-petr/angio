package com.angio.angiobackend.api.analyse.messaging.impl;

import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;
import com.angio.angiobackend.api.analyse.messaging.AnalyseResultListener;
import com.angio.angiobackend.api.analyse.service.impl.AnalyseServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
@Profile("!disablejms")
public class AnalyseResultListenerImpl implements AnalyseResultListener {

    private final AnalyseServiceImpl analyseService;

    @Override
    @JmsListener(destination = "${angiobackend.app.jms.analyse-results-queue}")
    public void receiveAnalyseResult(AnalyseJmsDto result) {
        log.info("receiveAnalyseResult() - start, received: {}", result);

        log.info("receiveAnalyseResult() - save result");
        analyseService.saveExecutedAnalyse(result);

        log.info("receiveAnalyseResult() - end");
    }
}
