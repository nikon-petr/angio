package com.angio.analyseexecutor.analyse.messaging;

import com.angio.analyseexecutor.analyse.dto.AnalyseDto;
import com.angio.analyseexecutor.analyse.service.AnalyseExecutorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class AnalyseListener {

    private final AnalyseExecutorService analyseExecutorService;
    private final AnalyseSender analyseSender;

    @JmsListener(destination = "${analyseexecutor.app.jms.analyse-execute-request}")
    public void receiveAnalyseToExecute(AnalyseDto analyse) {

        log.debug("receiveAnalyseToExecute() - start, analyse received to execute: {}", analyse);

        try {
            log.debug("receiveAnalyseToExecute() - execute analyse");
            analyse = analyseExecutorService.executeAnalyse(analyse);
            log.info("receiveAnalyseToExecute() - execution success");
        } catch (Exception e) {
            log.info("receiveAnalyseToExecute() - execution failed");
            log.warn("Execution failed", e);
            analyse
                    .setBloodFlowAnalyse(null)
                    .setGeometricAnalyse(null);
        } finally {

            log.debug("receiveAnalyseToExecute() - send result: {}", analyse);
            analyseSender.sendAnalyseResult(analyse);

            log.debug("receiveAnalyseToExecute() - end");
        }
    }
}
