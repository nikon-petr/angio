package com.angio.angiobackend.analyse.services.impl;

import com.angio.angiobackend.analyse.repositories.AnalyseBloodFlowCrudRepository;
import com.angio.angiobackend.analyse.services.AnalyseBloodFlowService;
import com.angio.angiobackend.analyse.services.DensityService;
import com.angio.angiobackend.analyse.services.IshemiaService;
import com.angio.angiobackend.analyse.services.MakulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("analyseBloodFlowService")
public class AnalyseBloodFlowServiceImpl implements AnalyseBloodFlowService {

    private final AnalyseBloodFlowCrudRepository analyseBloodFlowCrudRepository;
    private final DensityService densityService;
    private final IshemiaService ishemiaService;
    private final MakulaService makulaService;


    @Autowired
    public AnalyseBloodFlowServiceImpl(
            AnalyseBloodFlowCrudRepository analyseBloodFlowCrudRepository,
            DensityService densityService,
            IshemiaService ishemiaService,
            MakulaService makulaService) {
        this.analyseBloodFlowCrudRepository = analyseBloodFlowCrudRepository;
        this.densityService = densityService;
        this.ishemiaService = ishemiaService;
        this.makulaService = makulaService;
    }
}
