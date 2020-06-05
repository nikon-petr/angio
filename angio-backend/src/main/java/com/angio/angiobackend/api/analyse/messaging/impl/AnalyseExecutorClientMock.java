package com.angio.angiobackend.api.analyse.messaging.impl;

import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;
import com.angio.angiobackend.api.analyse.messaging.AnalyseExecutorClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("disablejms")
public class AnalyseExecutorClientMock implements AnalyseExecutorClient {

    @Override
    public void execute(AnalyseJmsDto analyse) {
        log.debug("execute() - start");
        log.debug("execute() - mock method called because 'disablejms' profile is active");
        log.debug("execute() - end");
    }
}
