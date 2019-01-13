package com.angio.analyseexecutor.analyse.messaging;

import com.angio.analyseexecutor.AnalyseExecutorProperties;
import com.angio.analyseexecutor.analyse.dto.AnalyseInfoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AnalyseSender {

    private final AnalyseExecutorProperties properties;
    private final JmsTemplate jmsTemplate;

    public void sendAnalyseResult(AnalyseInfoDto analyse) {

        log.info("sendAnalyseResult() - start");
        jmsTemplate.convertAndSend(properties.getJms().getAnalyseToExecuteQueue(), analyse);

        log.info("sendAnalyseResult() - end");
    }
}
