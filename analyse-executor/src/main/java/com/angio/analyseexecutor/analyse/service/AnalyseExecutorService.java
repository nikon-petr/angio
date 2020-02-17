package com.angio.analyseexecutor.analyse.service;

import com.angio.analyseexecutor.AnalyseExecutorProperties;
import com.angio.analyseexecutor.analyse.dto.AnalyseDto;
import com.angio.analyseexecutor.analyse.dto.BloodFlowAnalyseDto;
import com.angio.analyseexecutor.analyse.dto.DensityDto;
import com.angio.analyseexecutor.analyse.dto.GeometricAnalyseDto;
import com.angio.analyseexecutor.analyse.dto.IschemiaDto;
import com.angio.analyseexecutor.analyse.dto.MaculaDto;
import com.angio.analyseexecutor.analyse.dto.VesselDto;
import com.angio.analyseexecutor.analyse.matlab.bloodflow.BloodFlowAnalyseAdapter;
import com.angio.analyseexecutor.analyse.matlab.bloodflow.BloodFlowAnalyseResult;
import com.angio.analyseexecutor.analyse.matlab.geometric.GeometricAnalyseAdapter;
import com.angio.analyseexecutor.analyse.matlab.geometric.model.GeometricAnalyseModel;
import com.angio.analyseexecutor.analyse.matlab.geometric.model.VesselModel;
import com.angio.analyseexecutor.uploads.UploadsDao;
import com.angio.analyseexecutor.uploads.dto.StaticFileDto;
import com.angio.analyseexecutor.util.FileUtil;
import com.mathworks.toolbox.javabuilder.MWException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class AnalyseExecutorService {

    private final AnalyseExecutorProperties props;
    private final UploadsDao dao;

    public AnalyseDto executeAnalyse(AnalyseDto analyse) throws IOException, MWException, SQLException {

        log.info("startNewAnalyse() - start");

        File originalImage = new File(props.getUploadDirectory(), analyse.getOriginalImage().getFilename());

        log.info("startNewAnalyse() - geometric analyse start");
        GeometricAnalyseModel geometricAnalyseModel = new GeometricAnalyseAdapter().runAnalyse(originalImage.getAbsolutePath());

        log.info("startNewAnalyse() - save images of geometric analyse");
        List<StaticFileDto> uploads = new ArrayList<>();
        StaticFileDto binarizedImage = StaticFileDto.of(FileUtil.saveImage(geometricAnalyseModel.getBinarized(), props.getResultImageFormat(), props.getUploadDirectory()));
        StaticFileDto skelImage =  StaticFileDto.of(FileUtil.saveImage(geometricAnalyseModel.getSkel(), props.getResultImageFormat(), props.getUploadDirectory()));
        uploads.add(binarizedImage);
        uploads.add(skelImage);

        GeometricAnalyseDto geometricAnalyse = new GeometricAnalyseDto();
        geometricAnalyse.setBinarizedImage(binarizedImage);
        geometricAnalyse.setSkeletonizedImage(skelImage);


        List<VesselDto> vessels = new ArrayList<>();
        for (VesselModel vesselModel : geometricAnalyseModel.getAnalyseResult()) {
            StaticFileDto vesselImage = StaticFileDto.of(FileUtil.saveImage(vesselModel.getVesselImage(), props.getResultImageFormat(), props.getUploadDirectory()));
            StaticFileDto mainVesselImage = StaticFileDto.of(FileUtil.saveImage(vesselModel.getMainVessel(), props.getResultImageFormat(), props.getUploadDirectory()));
            uploads.add(vesselImage);
            uploads.add(mainVesselImage);

            VesselDto vessel = new VesselDto();
            vessel.setVesselImage(vesselImage);
            vessel.setMainVesselImage(mainVesselImage);
            vessel.setTortuosityDegree((float) vesselModel.getTortuosity());
            vessel.setCountOfBranches(vesselModel.getCountOfBranchesOf1Orders());
            vessel.setBranchingDegree((float) vesselModel.getBranching());
            vessel.setArea((float) vesselModel.getArea());
            vessel.setAreaPercent((float) vesselModel.getAreaPercent());

            vessels.add(vessel);
        }

        geometricAnalyse.setVessels(vessels);

        analyse.setGeometricAnalyse(geometricAnalyse);
        log.info("startNewAnalyse() - blood flow analyse end");

        log.info("startNewAnalyse() - blood flow analyse");
        BloodFlowAnalyseResult bloodFlowAnalyseResult = new BloodFlowAnalyseAdapter().runAnalyse(originalImage.getAbsolutePath());

        log.info("startNewAnalyse() - save images of blood flow analyse");
        StaticFileDto ishemiaImage = StaticFileDto.of(FileUtil.saveImage(bloodFlowAnalyseResult.getIshemiaImage(), props.getResultImageFormat(), props.getUploadDirectory()));
        StaticFileDto densityImage = StaticFileDto.of(FileUtil.saveImage(bloodFlowAnalyseResult.getCapillarDensityImage(), props.getResultImageFormat(), props.getUploadDirectory()));
        uploads.add(ishemiaImage);
        uploads.add(densityImage);

        BloodFlowAnalyseDto bloodFlowAnalyse = new BloodFlowAnalyseDto();

        bloodFlowAnalyse.setIschemiaImage(ishemiaImage);
        bloodFlowAnalyse.setDensityImage(densityImage);

        List<IschemiaDto> ischemias = new ArrayList<>();
        for (int i = 0; i < bloodFlowAnalyseResult.getIshemiaArea().length; i++) {
            ischemias.add(new IschemiaDto(
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

        MaculaDto makula = new MaculaDto(
                bloodFlowAnalyseResult.getMakulaArea(),
                bloodFlowAnalyseResult.getMakulaRadius(),
                bloodFlowAnalyseResult.getMakulaCenter()[0],
                bloodFlowAnalyseResult.getMakulaCenter()[1]
        );

        bloodFlowAnalyse.setIschemias(ischemias);
        bloodFlowAnalyse.setDensities(densities);
        bloodFlowAnalyse.setMacula(makula);

        analyse.setBloodFlowAnalyse(bloodFlowAnalyse);

        log.info("startNewAnalyse() - insert image information to db");
        dao.insertImageInfo(uploads);

        log.info("startNewAnalyse() - blood flow analyse end");
        log.info("startNewAnalyse() - end");
        return analyse;
    }
}
