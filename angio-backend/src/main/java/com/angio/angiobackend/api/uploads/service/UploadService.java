package com.angio.angiobackend.api.uploads.service;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import com.angio.angiobackend.api.uploads.entity.StaticFileEntity;
import com.angio.angiobackend.api.uploads.mapper.UploadMapper;
import com.angio.angiobackend.api.uploads.repository.UploadRepository;
import com.angio.angiobackend.api.uploads.type.FileType;
import com.angio.angiobackend.util.FileUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Service
public class UploadService {

    private final AngioBackendProperties props;
    private final UploadRepository uploadRepository;
    private final UploadMapper uploadMapper;

    public StaticFileDto uploadImage(MultipartFile file) throws IOException {

        log.info("uploadImage() - start");
        log.info("uploadImage() - save image to file system");
        String savedFilename = FileUtils.saveFile(file, props.getImageUploadExtensions(),
                props.getUploadDirectory());

        log.info("uploadImage() - save image data to database");
        StaticFileEntity savedImage = uploadRepository.save(new StaticFileEntity(null, FileType.IMAGE, savedFilename));

        log.info("uploadImage() - map image data");
        StaticFileDto savedStaticFileDto = uploadMapper.toExternalDto(savedImage);

        log.info("uploadImage() - result: {}", savedStaticFileDto);
        log.info("uploadImage() - end");
        return savedStaticFileDto;
    }

    public StaticFileDto uploadDocument(MultipartFile file) throws IOException {

        log.info("uploadDocument() - start");
        log.info("uploadDocument() - save document to file system");
        String savedFilename = FileUtils.saveFile(file, props.getDocumentUploadExtensions(),
                props.getUploadDirectory());

        log.info("uploadDocument() - save document data to database");
        StaticFileEntity savedImage = uploadRepository.save(new StaticFileEntity(null, FileType.DOCUMENT, savedFilename));

        log.info("uploadDocument() - map document data");
        StaticFileDto savedStaticFileDto = uploadMapper.toExternalDto(savedImage);

        log.info("uploadDocument() - result: {}", savedStaticFileDto);
        log.info("uploadDocument() - end");
        return savedStaticFileDto;
    }
}
