package com.angio.analyseexecutor.analyse.messaging;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@NoArgsConstructor
@Component
public class AnalyseListener {

    @JmsListener(destination = "${analyse.executor.app.jms.analyse-to-execute-queue}")
    public void receiveAnalyseToExecute(String analyse) {
        log.info("receiveAnalyseToExecute() - start");
        log.info("receiveAnalyseToExecute() - analyse received to execute: " + analyse);
        log.info("receiveAnalyseToExecute() - end");
    }
}
