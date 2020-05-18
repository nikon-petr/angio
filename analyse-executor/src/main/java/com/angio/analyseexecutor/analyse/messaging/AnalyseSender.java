package com.angio.analyseexecutor.analyse.messaging;

import com.angio.analyseexecutor.AnalyseExecutorProperties;
import com.angio.analyseexecutor.analyse.dto.AnalyseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AnalyseSender {

    private final AnalyseExecutorProperties props;
    private final JmsTemplate jmsTemplate;

    public void sendAnalyseResult(AnalyseDto analyse) {

        log.debug("sendAnalyseResult() - start");
        log.debug("sendAnalyseResult() - payload: {}", analyse);
        jmsTemplate.convertAndSend(props.getJms().getAnalyseResultsQueue(), analyse);

        log.debug("sendAnalyseResult() - end");
    }
}
