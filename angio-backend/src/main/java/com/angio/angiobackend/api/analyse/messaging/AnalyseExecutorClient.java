package com.angio.angiobackend.api.analyse.messaging;

import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;

public interface AnalyseExecutorClient {

    void execute(AnalyseJmsDto analyse);
}
