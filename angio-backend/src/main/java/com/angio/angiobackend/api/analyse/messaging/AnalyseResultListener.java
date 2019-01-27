package com.angio.angiobackend.api.analyse.messaging;

import com.angio.angiobackend.api.analyse.dto.AnalyseDto;
import com.angio.angiobackend.api.analyse.service.impl.AnalyseServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class AnalyseResultListener {

    private final AnalyseServiceImpl analyseService;

    @JmsListener(destination = "${angio.app.jms.analyse-results-queue}")
    public void receiveAnalyseResult(AnalyseDto result) {
        log.info("receiveAnalyseResult() - start, received: {}", result);

        log.info("receiveAnalyseResult() - save result");
        analyseService.saveExecutedAnalyse(result);

        log.info("receiveAnalyseResult() - end");
    }
}
