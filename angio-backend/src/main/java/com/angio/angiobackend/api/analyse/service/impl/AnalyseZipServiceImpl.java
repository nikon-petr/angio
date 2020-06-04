package com.angio.angiobackend.api.analyse.service.impl;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.analyse.dto.DetailedAnalyseDto;
import com.angio.angiobackend.api.analyse.dto.VesselDto;
import com.angio.angiobackend.api.analyse.service.AnalyseZipService;
import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.exception.OperationException;
import com.angio.angiobackend.api.uploads.type.FileType;
import com.angio.angiobackend.util.IoStreamUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class AnalyseZipServiceImpl implements AnalyseZipService {

    private static final String ORIGINAL_IMAGE_TEMPLATE = "Original.%s";
    private static final String GEOMETRIC_SKELETONIZED_IMAGE_TEMPLATE = "Geometric/Skeletonized.%s";
    private static final String GEOMETRIC_BINARIZED_IMAGE_TEMPLATE = "Geometric/Binarized.%s";
    private static final String GEOMETRIC_VESSEL_MAIN_IMAGE_TEMPLATE = "Geometric/Vessels/Main Vessel %s.%s";
    private static final String GEOMETRIC_VESSEL_IMAGE_TEMPLATE = "Geometric/Vessels/Vessel %s.%s";
    private static final String BLOODFLOW_MACULA_ISCHEMIA_IMAGE_TEMPLATE = "Blood Flow/Macula/Ischemia And Macula.%s";
    private static final String BLOODFLOW_MACULA_DENSITY_IMAGE_TEMPLATE = "Blood Flow/Macula/Density.%s";
    private static final String BLOODFLOW_OPTIC_DISC_DENSITY_IMAGE_TEMPLATE = "Blood Flow/Optic Disc/Density.%s";

    private final AngioBackendProperties props;
    private final DynamicLocaleMessageSourceAccessor msa;

    @Override
    public byte[] createArchive(DetailedAnalyseDto dto) {
        log.debug("createArchive() - start");

        Map<String, File> images = new HashMap<>();

        log.debug("createArchive() - extract original image");
        extractOriginalImage(dto, images);

        if (dto.getExecutionConfiguration().getGeometric()) {
            log.debug("createArchive() - extract images from geometric analyse");
            extractImagesFromGeometric(dto, images);
        }

        if (dto.getExecutionConfiguration().getMaculaBloodFlow()) {
            log.debug("createArchive() - extract images from macula bloodflow analyse");
            extractImagesForMaculaBloodFlow(dto, images);
        }

        if (dto.getExecutionConfiguration().getOpticDiskBloodFlow()) {
            log.debug("createArchive() - extract images from optic disk bloodflow analyse");
            extractImagesForopticDiskBloodFlow(dto, images);
        }

        log.debug("createArchive() - put images to zip archive");
        byte[] archive = putImagesToArchive(dto, images);

        log.debug("createArchive() - end");
        return archive;
    }

    private byte[] putImagesToArchive(DetailedAnalyseDto dto, Map<String, File> images) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ZipOutputStream zip = new ZipOutputStream(bos)) {

            for (String imageName : images.keySet()) {
                ZipEntry zipEntry = new ZipEntry(imageName);
                zip.putNextEntry(zipEntry);
                IoStreamUtils.copy(images.get(imageName), zip);
                zip.closeEntry();
            }

            zip.finish();

            return bos.toByteArray();
        } catch (IOException e) {
            throw new OperationException(msa.getMessage("errors.api.analyse.archive", new Object[]{dto.getId()}));
        }
    }

    private void extractOriginalImage(DetailedAnalyseDto dto, Map<String, File> images) {
        if (dto.getOriginalImage().getType() == FileType.VIDEO) {
            return;
        }

        String originalImageName = format(ORIGINAL_IMAGE_TEMPLATE,
                FilenameUtils.getExtension(dto.getOriginalImage().getFilename()));
        images.put(originalImageName, new File(props.getUploadDirectory(), dto.getOriginalImage().getFilename()));
    }


    private void extractImagesFromGeometric(DetailedAnalyseDto dto, Map<String, File> images) {
        String skeletonizedImageName = format(GEOMETRIC_SKELETONIZED_IMAGE_TEMPLATE,
                FilenameUtils.getExtension(dto.getGeometricAnalyse().getSkeletonizedImage().getFilename()));
        images.put(skeletonizedImageName, new File(props.getUploadDirectory(),
                dto.getGeometricAnalyse().getSkeletonizedImage().getFilename()));
        String binarizedImageName = format(GEOMETRIC_BINARIZED_IMAGE_TEMPLATE,
                FilenameUtils.getExtension(dto.getGeometricAnalyse().getBinarizedImage().getFilename()));
        images.put(binarizedImageName,
                new File(props.getUploadDirectory(), dto.getGeometricAnalyse().getBinarizedImage().getFilename()));

        for (VesselDto vessel : dto.getGeometricAnalyse().getVessels()) {
            String vesselMainImageName = format(GEOMETRIC_VESSEL_MAIN_IMAGE_TEMPLATE, vessel.getId(),
                    FilenameUtils.getExtension(vessel.getMainVesselImage().getFilename()));
            images.put(vesselMainImageName,
                    new File(props.getUploadDirectory(), vessel.getMainVesselImage().getFilename()));

            String vesselImageName = format(GEOMETRIC_VESSEL_IMAGE_TEMPLATE, vessel.getId(),
                    FilenameUtils.getExtension(vessel.getVesselImage().getFilename()));
            images.put(vesselImageName,
                    new File(props.getUploadDirectory(), vessel.getVesselImage().getFilename()));
        }
    }

    private void extractImagesForMaculaBloodFlow(DetailedAnalyseDto dto, Map<String, File> images) {
        String ischemiaImageName = format(BLOODFLOW_MACULA_ISCHEMIA_IMAGE_TEMPLATE,
                FilenameUtils.getExtension(dto.getBloodFlowAnalyse().getIschemiaImage().getFilename()));
        images.put(ischemiaImageName,
                new File(props.getUploadDirectory(),
                        dto.getBloodFlowAnalyse().getIschemiaImage().getFilename()));

        String densityImageName = format(BLOODFLOW_MACULA_DENSITY_IMAGE_TEMPLATE,
                FilenameUtils.getExtension(dto.getBloodFlowAnalyse().getDensityImage().getFilename()));
        images.put(densityImageName,
                new File(props.getUploadDirectory(), dto.getBloodFlowAnalyse().getDensityImage().getFilename()));
    }


    private void extractImagesForopticDiskBloodFlow(DetailedAnalyseDto dto, Map<String, File> images) {
        String densityImageName = format(BLOODFLOW_OPTIC_DISC_DENSITY_IMAGE_TEMPLATE,
                FilenameUtils.getExtension(dto.getBloodFlowAnalyse().getDensityImage().getFilename()));
        images.put(densityImageName,
                new File(props.getUploadDirectory(), dto.getBloodFlowAnalyse().getDensityImage().getFilename()));
    }
}
