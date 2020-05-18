package com.angio.angiobackend.api.analyse.messaging.impl;

import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;
import com.angio.angiobackend.api.analyse.messaging.AnalyseToExecuteSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("disablejms")
public class AnalyseToExecuteSenderMock implements AnalyseToExecuteSender {

    @Override
    public void sendAnalyseToExecute(AnalyseJmsDto analyse) {
        log.debug("sendAnalyseToExecute() - start");
        log.debug("sendAnalyseToExecute() - mock method called because 'disablejms' profile is active");
        log.debug("sendAnalyseToExecute() - end");
    }
}
