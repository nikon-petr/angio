package com.angio.angiobackend.api.analyse.messaging;

import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;

public interface AnalyseToExecuteSender {

    void sendAnalyseToExecute(AnalyseJmsDto analyse);
}
