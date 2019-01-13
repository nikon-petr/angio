package com.angio.analyseexecutor.analyse.service;

import com.angio.analyseexecutor.AnalyseExecutorProperties;
import com.angio.analyseexecutor.analyse.dto.AnalyseInfoDto;
import com.angio.analyseexecutor.analyse.dto.BloodFlowAnalyseDto;
import com.angio.analyseexecutor.analyse.dto.DensityDto;
import com.angio.analyseexecutor.analyse.dto.GeometricAnalyseDto;
import com.angio.analyseexecutor.analyse.dto.IshemiaDto;
import com.angio.analyseexecutor.analyse.dto.MakulaDto;
import com.angio.analyseexecutor.analyse.dto.VesselDto;
import com.angio.analyseexecutor.analyse.matlab.bloodflow.BloodFlowAnalyseAdapter;
import com.angio.analyseexecutor.analyse.matlab.bloodflow.BloodFlowAnalyseResult;
import com.angio.analyseexecutor.analyse.matlab.geometric.GeometricAnalyseAdapter;
import com.angio.analyseexecutor.analyse.matlab.geometric.model.GeometricAnalyseModel;
import com.angio.analyseexecutor.analyse.matlab.geometric.model.VesselModel;
import com.angio.analyseexecutor.common.dto.UploadDto;
import com.angio.analyseexecutor.util.FileUtil;
import com.mathworks.toolbox.javabuilder.MWException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class AnalyseExecutorService {

    private final AnalyseExecutorProperties properties;

    public AnalyseInfoDto executeAnalyse(AnalyseInfoDto analyse) throws IOException, MWException {
        log.info("startNewAnalyse() - start");

        log.info("startNewAnalyse() - geometric analyse");
        GeometricAnalyseModel geometricAnalyseModel = new GeometricAnalyseAdapter().runAnalyse(URI.create(analyse.getOriginalImage().getUrl()));

        log.info("startNewAnalyse() - save images of geometric analyse");
        String binarizedUri = FileUtil.saveImage(geometricAnalyseModel.getBinarized(), properties.getResultImageFormat(), properties.getImageUploadDirectory());

        String skelUri = FileUtil.saveImage(geometricAnalyseModel.getSkel(), properties.getResultImageFormat(), properties.getImageUploadDirectory());
        GeometricAnalyseDto geometricAnalyse = analyse.getGeometricAnalyse();
        geometricAnalyse.setBinarizedImage(UploadDto.of(binarizedUri));
        geometricAnalyse.setSkelImage(UploadDto.of(skelUri));

        List<VesselDto> vessels = new ArrayList<>();
        for (VesselModel vesselModel : geometricAnalyseModel.getAnalyseResult()) {
            String vesselImage = FileUtil.saveImage(vesselModel.getVesselImage(), properties.getResultImageFormat(), properties.getImageUploadDirectory());
            String mainVessel = FileUtil.saveImage(vesselModel.getMainVessel(), properties.getResultImageFormat(), properties.getImageUploadDirectory());

            VesselDto vessel = new VesselDto();
            vessel.setVesselImage(UploadDto.of(vesselImage));
            vessel.setMainVesselImage(UploadDto.of(mainVessel));
            vessel.setTortuosityDegree((float) vesselModel.getTortuosity());
            vessel.setCountOfBranches(vesselModel.getCountOfBranchesOf1Orders());
            vessel.setBranchingDegree((float) vesselModel.getBranching());
            vessel.setArea((float) vesselModel.getArea());
            vessel.setAreaPercent((float) vesselModel.getAreaPercent());

            vessels.add(vessel);
        }

        geometricAnalyse.setVessels(vessels);

        log.info("startNewAnalyse() - blood flow analyse");
        BloodFlowAnalyseResult bloodFlowAnalyseResult = new BloodFlowAnalyseAdapter().runAnalyse(URI.create(analyse.getOriginalImage().getUrl()));

        log.info("startNewAnalyse() - save images of blood flow analyse");
        String ishemiaImageUri = FileUtil.saveImage(bloodFlowAnalyseResult.getIshemiaImage(), properties.getResultImageFormat(), properties.getImageUploadDirectory());
        String densityImageUri = FileUtil.saveImage(bloodFlowAnalyseResult.getCapillarDensityImage(), properties.getResultImageFormat(), properties.getImageUploadDirectory());


        BloodFlowAnalyseDto bloodFlowAnalyse = new BloodFlowAnalyseDto();

        bloodFlowAnalyse.setIshemiaImage(UploadDto.of(ishemiaImageUri));
        bloodFlowAnalyse.setDensityImage(UploadDto.of(densityImageUri));

        List<IshemiaDto> ischemias = new ArrayList<>();
        for (int i = 0; i < bloodFlowAnalyseResult.getIshemiaArea().length; i++) {
            ischemias.add(new IshemiaDto(
                    null,
                    bloodFlowAnalyseResult.getIshemiaArea()[i],
                    i + 1,
                    bloodFlowAnalyseResult.getIshemiaCenter()[i][0],
                    bloodFlowAnalyseResult.getIshemiaCenter()[i][1]));
        }

        List<DensityDto> densities = new ArrayList<>();
        for (int i = 0; i < bloodFlowAnalyseResult.getCapilarDensities().length; i++) {
            densities.add(new DensityDto(
                    null,
                    i + 1,
                    bloodFlowAnalyseResult.getCapilarDensities()[i]));
        }

        MakulaDto makula = new MakulaDto(
                null,
                bloodFlowAnalyseResult.getMakulaArea(),
                bloodFlowAnalyseResult.getMakulaRadius(),
                bloodFlowAnalyseResult.getMakulaCenter()[0],
                bloodFlowAnalyseResult.getMakulaCenter()[1]
        );

        bloodFlowAnalyse.setIschemias(ischemias);
        bloodFlowAnalyse.setDensities(densities);
        bloodFlowAnalyse.setMakula(makula);

        analyse.setBloodFlowAnalyse(bloodFlowAnalyse);

        log.info("startNewAnalyse() - end");
        return analyse;
    }
}
