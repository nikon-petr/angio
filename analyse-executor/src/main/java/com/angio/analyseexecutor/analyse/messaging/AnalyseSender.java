package com.angio.analyseexecutor.analyse.messaging;

import com.angio.analyseexecutor.AnalyseExecutorProperties;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AnalyseSender {

    private final AnalyseExecutorProperties properties;
    private final JmsTemplate jmsTemplate;

    public void sendResult(String analyse) {
        jmsTemplate.convertAndSend(properties.getJms().getAnalyseToExecuteQueue(), analyse);
    }
}
