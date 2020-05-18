package com.angio.angiobackend.api.analyse.messaging.impl;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;
import com.angio.angiobackend.api.analyse.messaging.AnalyseToExecuteSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
@Profile("!disablejms")
public class AnalyseToExecuteSenderImpl implements AnalyseToExecuteSender {

    private final AngioBackendProperties props;
    private final JmsTemplate jmsTemplate;

    @Override
    public void sendAnalyseToExecute(AnalyseJmsDto analyse) {

        log.info("sendAnalyseToExecute() - start");
        jmsTemplate.convertAndSend(props.getJms().getAnalyseToExecuteQueue(), analyse);

        log.info("sendAnalyseToExecute() - end");
    }
}
