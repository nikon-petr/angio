package com.angio.angiobackend.api.uploads.service.impl;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import com.angio.angiobackend.api.uploads.entity.StaticFileEntity;
import com.angio.angiobackend.api.uploads.mapper.UploadMapper;
import com.angio.angiobackend.api.uploads.repository.UploadRepository;
import com.angio.angiobackend.api.uploads.service.UploadService;
import com.angio.angiobackend.api.uploads.type.FileType;
import com.angio.angiobackend.util.FileUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@AllArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {

    private final AngioBackendProperties props;
    private final UploadRepository uploadRepository;
    private final UploadMapper uploadMapper;
    private final DynamicLocaleMessageSourceAccessor msa;

    @Override
    @Transactional
    public StaticFileDto uploadImage(@NonNull MultipartFile file) throws IOException {

        log.info("uploadImage() - start");
        log.info("uploadImage() - save image to file system");
        validateFile(file);
        String savedFilename = FileUtils.saveFile(file, props.getUploadDirectory());

        log.info("uploadImage() - save image data to database");
        StaticFileEntity savedImage = uploadRepository.save(new StaticFileEntity(null, FileType.IMAGE, savedFilename));

        log.info("uploadImage() - map image data");
        StaticFileDto savedStaticFileDto = uploadMapper.toExternalDto(savedImage);

        log.info("uploadImage() - result: {}", savedStaticFileDto);
        log.info("uploadImage() - end");
        return savedStaticFileDto;
    }

    @Override
    @Transactional
    public StaticFileDto uploadDocument(@NonNull MultipartFile file) throws IOException {

        log.info("uploadDocument() - start");
        log.info("uploadDocument() - save document to file system");
        validateFile(file);
        String savedFilename = FileUtils.saveFile(file, props.getUploadDirectory());

        log.info("uploadDocument() - save document data to database");
        StaticFileEntity savedImage = uploadRepository.save(new StaticFileEntity(null, FileType.DOCUMENT, savedFilename));

        log.info("uploadDocument() - map document data");
        StaticFileDto savedStaticFileDto = uploadMapper.toExternalDto(savedImage);

        log.info("uploadDocument() - result: {}", savedStaticFileDto);
        log.info("uploadDocument() - end");
        return savedStaticFileDto;
    }

    private void validateFile(MultipartFile file) {
        if(!FilenameUtils.isExtension(file.getOriginalFilename(), props.getImageUploadExtensions())) {
            throw new IllegalArgumentException(msa.getMessage("errors.api.uploads.extensionNoAllowed",
                    new Object[] {
                            FilenameUtils.getExtension(file.getOriginalFilename()),
                            Arrays.toString(props.getImageUploadExtensions())}));
        }

        if (file.isEmpty()) {
            throw new IllegalArgumentException(msa.getMessage("errors.api.uploads.fileIsEmpty"));
        }
    }
}
