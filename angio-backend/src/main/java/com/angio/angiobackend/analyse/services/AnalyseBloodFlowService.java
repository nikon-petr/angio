package com.angio.angiobackend.analyse.services;

import com.angio.angiobackend.analyse.entities.AnalyseBloodFlowEntity;
import com.angio.angiobackend.analyse.entities.AnalyseInfoEntity;
import com.angio.angiobackend.util.matlab.bloodflow.BloodFlowAnalyseResult;

public interface AnalyseBloodFlowService {
    AnalyseBloodFlowEntity addNewAnalyse(
            AnalyseInfoEntity analyseInfo,
            String ishemiaImagePath,
            String densityImagePath,
            BloodFlowAnalyseResult result);
}
