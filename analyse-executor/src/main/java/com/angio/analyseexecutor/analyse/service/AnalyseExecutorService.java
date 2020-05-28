package com.angio.analyseexecutor.analyse.service;

import com.angio.analyseexecutor.AnalyseExecutorProperties;
import com.angio.analyseexecutor.analyse.dto.AnalyseDto;
import com.angio.analyseexecutor.analyse.dto.BloodFlowAnalyseDto;
import com.angio.analyseexecutor.analyse.dto.DensityDto;
import com.angio.analyseexecutor.analyse.dto.DensityType;
import com.angio.analyseexecutor.analyse.dto.ExecutionConfigurationDto;
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class AnalyseExecutorService {

    private final BloodFlowAnalyseAdapter bloodFlowAnalyseAdapter;
    private final GeometricAnalyseAdapter geometricAnalyseAdapter;
    private final AnalyseExecutorProperties props;
    private final UploadsDao dao;

    public AnalyseDto executeAnalyse(AnalyseDto analyse) throws IOException, MWException, SQLException {

        log.info("startNewAnalyse() - start");

        log.debug("startNewAnalyse() - get original image");
        File originalImage = new File(props.getUploadDirectory(), analyse.getOriginalImage().getFilename());
        List<StaticFileDto> uploads = new ArrayList<>();

        log.debug("startNewAnalyse() - execute analyses by execution config");
        ExecutionConfigurationDto config = analyse.getExecutionConfiguration();

        log.debug("startNewAnalyse() - maculaBloodFlow willExecute: {}", config.getMaculaBloodFlow());
        if (config.getMaculaBloodFlow()) {
            maculaBloodFlow(analyse, originalImage, uploads);
        }

        log.debug("startNewAnalyse() - opticDiskBloodFlow willExecute: {}", config.getOpticDiskBloodFlow());
        if (config.getOpticDiskBloodFlow()) {
            opticDiskBloodFlowDensity(analyse, originalImage, uploads);
        }

        log.debug("startNewAnalyse() - geometric willExecute: {}", config.getGeometric());
        if (config.getGeometric()) {
            geometric(analyse, originalImage, uploads);
        }

        log.debug("startNewAnalyse() - profileCysticVolume willExecute: {}", config.getProfileCysticVolume());
        if (config.getProfileCysticVolume()) {
            profileCysticVolume(analyse, originalImage, uploads);
        }

        log.debug("startNewAnalyse() - profileRetinalPositiveExtremum willExecute: {}", config.getProfileRetinalPositiveExtremum());
        if (config.getProfileRetinalPositiveExtremum()) {
            profileRetinalPositiveExtremum(analyse, originalImage, uploads);
        }

        log.debug("maculaBloodFlow() - insert image information to db");
        dao.insertImageInfo(uploads);

        log.info("startNewAnalyse() - end");
        return analyse;
    }

    private void maculaBloodFlow(AnalyseDto analyse, File originalImage, List<StaticFileDto> uploads) throws
            IOException, MWException, SQLException {

        log.info("maculaBloodFlow() - start of blood flow analyse");
        BloodFlowAnalyseResult bloodFlowAnalyseResult = bloodFlowAnalyseAdapter.runAnalyse(originalImage.getAbsolutePath());

        log.debug("maculaBloodFlow() - save images of blood flow analyse");
        StaticFileDto ishemiaImage = saveImage(bloodFlowAnalyseResult.getIshemiaImage());
        StaticFileDto densityImage = saveImage(bloodFlowAnalyseResult.getCapillarDensityImage());
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
                    bloodFlowAnalyseResult.getCapilarDensities()[i],
                    DensityType.MACULA));
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

        log.info("maculaBloodFlow() - end of blood flow analyse");
    }

    private void opticDiskBloodFlowDensity(AnalyseDto analyse, File originalImage, List<StaticFileDto> uploads) {
        log.info("opticDiskBloodFlowDensity() - start");
        throw new UnsupportedOperationException("analyse of optiс disk blood flow is not implemented");
    }

    private void geometric(AnalyseDto analyse, File originalImage, List<StaticFileDto> uploads) throws
            MWException, IOException {

        log.info("geometric() - start of geometric analyse");
        GeometricAnalyseModel geometricAnalyseModel = geometricAnalyseAdapter.runAnalyse(originalImage.getAbsolutePath());

        log.debug("geometric() - save images of geometric analyse");
        StaticFileDto binarizedImage = saveImage(geometricAnalyseModel.getBinarized());
        StaticFileDto skelImage = saveImage(geometricAnalyseModel.getSkel());
        uploads.add(binarizedImage);
        uploads.add(skelImage);

        GeometricAnalyseDto geometricAnalyse = new GeometricAnalyseDto();
        geometricAnalyse.setBinarizedImage(binarizedImage);
        geometricAnalyse.setSkeletonizedImage(skelImage);


        List<VesselDto> vessels = new ArrayList<>();
        for (VesselModel vesselModel : geometricAnalyseModel.getAnalyseResult()) {
            StaticFileDto vesselImage = saveImage(vesselModel.getVesselImage());
            StaticFileDto mainVesselImage = saveImage(vesselModel.getMainVessel());
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
        log.info("geometric() - end of geometric analyse");
    }

    private void profileCysticVolume(AnalyseDto analyse, File originalImage, List<StaticFileDto> uploads) {
        log.info("profileCysticVolume() - start");

//        if (analyse.getProfileAnalyse() == null) {
//            analyse.setProfileAnalyse(new ProfileAnalyseDto());
//        }
//
//        CysticVolumeDto cysticVolume = new CysticVolumeDto()
//        .setCysticVolume(12.0005)
//        .setAngiogramImage(analyse.getOriginalImage())
//        .setProfileImage(analyse.getOriginalImage());
//
//        analyse.getProfileAnalyse().setCysticVolume(cysticVolume);

        throw new UnsupportedOperationException("analyse of cystic volume is not implemented");
    }

    private void profileRetinalPositiveExtremum(AnalyseDto analyse, File originalImage, List<StaticFileDto> uploads) {
        log.info("profileRetinalPositiveExtremum() - start");

//        if (analyse.getProfileAnalyse() == null) {
//            analyse.setProfileAnalyse(new ProfileAnalyseDto());
//        }
//
//        RetinalPositiveExtremumDto retinalPositiveExtremum = new RetinalPositiveExtremumDto()
//                .setExtremumValue(540.80)
//                .setAngiogramImage(analyse.getOriginalImage())
//                .setProfileImage(analyse.getOriginalImage());
//
//        analyse.getProfileAnalyse().setRetinalPositiveExtremum(retinalPositiveExtremum);

        throw new UnsupportedOperationException("analyse of retinal positive extremum is not implemented");
    }

    private StaticFileDto saveImage(BufferedImage img) throws IOException {
        return StaticFileDto.of(FileUtil.saveImage(img, props.getResultImageFormat(), props.getUploadDirectory()));
    }
}
