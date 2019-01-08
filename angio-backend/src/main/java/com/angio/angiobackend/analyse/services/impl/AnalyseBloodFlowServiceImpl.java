package com.angio.angiobackend.analyse.services.impl;

import com.angio.angiobackend.analyse.entities.*;
import com.angio.angiobackend.analyse.repositories.AnalyseBloodFlowCrudRepository;
import com.angio.angiobackend.analyse.services.AnalyseBloodFlowService;
import com.angio.angiobackend.analyse.services.DensityService;
import com.angio.angiobackend.analyse.services.IshemiaService;
import com.angio.angiobackend.analyse.services.MakulaService;
import com.angio.angiobackend.util.matlab.bloodflow.BloodFlowAnalyseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

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


    @Override
    @Transactional
    public AnalyseBloodFlowEntity addNewAnalyse(
            AnalyseInfoEntity analyseInfo,
            String ishemiaImagePath,
            String densityImagePath,
            BloodFlowAnalyseResult result) {
        AnalyseBloodFlowEntity analyse = new AnalyseBloodFlowEntity(
                analyseInfo,
                ishemiaImagePath,
                densityImagePath);
        analyse = analyseBloodFlowCrudRepository.save(analyse);

        Set<IshemiaEntity> ischemias = new HashSet<>();
        for (int i = 0; i < result.getIshemiaArea().length; i++) {
            ischemias.add(new IshemiaEntity(
                    analyse,
                    result.getIshemiaArea()[i],
                    i + 1,
                    result.getIshemiaCenter()[i][0],
                    result.getIshemiaCenter()[i][1]));
        }

        Set<DensityEntity> densities = new HashSet<>();
        for (int i = 0; i < result.getCapilarDensities().length; i++) {
            densities.add(new DensityEntity(
                    analyse,
                    i + 1,
                    result.getCapilarDensities()[i]));
        }

        MakulaEntity makula = new MakulaEntity(
                analyse,
                result.getMakulaArea(),
                result.getMakulaRadius(),
                result.getMakulaCenter()[0],
                result.getMakulaCenter()[1]
        );

        ishemiaService.addNewIshemias(ischemias);
        densityService.addNewDensities(densities);
        makulaService.addNewMakula(makula);

        return analyse;
    }
}
