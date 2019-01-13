package com.angio.angiobackend.analyse.messaging;

import com.angio.angiobackend.analyse.dto.AnalyseInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AnalyseResultListener {

    @JmsListener(destination = "${angio.app.jms.analyse-to-execute-queue}")
    public void receiveAnalyseResult(AnalyseInfoDto result) {
        log.info("receiveAnalyseResult() - start, received: {}", result);
        log.info("receiveAnalyseResult() - end");
    }
}
