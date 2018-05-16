package com.angio.server.analyse.services;

import com.angio.server.analyse.entities.AnalyseBloodFlowEntity;
import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.util.matlab.bloodflow.BloodFlowAnalyseResult;

public interface AnalyseBloodFlowService {
    AnalyseBloodFlowEntity addNewAnalyse(
            AnalyseInfoEntity analyseInfo,
            String ishemiaImagePath,
            String densityImagePath,
            BloodFlowAnalyseResult result);
}
