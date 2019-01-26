package com.angio.angiobackend.api.analyse.messaging;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.analyse.dto.AnalyseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class AnalyseToExecuteSender {

    private final AngioBackendProperties props;
    private final JmsTemplate jmsTemplate;

    public void sendAnalyseToExecute(AnalyseDto analyse) {

        log.info("sendAnalyseToExecute() - start");
        jmsTemplate.convertAndSend(props.getJms().getAnalyseToExecuteQueue(), analyse);

        log.info("sendAnalyseToExecute() - end");
    }
}
