package com.angio.angiobackend.api.analyse.messaging;

import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;

public interface AnalyseResultListener {

    void receiveAnalyseResult(AnalyseJmsDto result);
}
