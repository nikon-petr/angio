package com.angio.angiobackend.uploads.service;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.uploads.dto.UploadDto;
import com.angio.angiobackend.uploads.entity.UploadEntity;
import com.angio.angiobackend.uploads.mapper.UploadMapper;
import com.angio.angiobackend.uploads.repository.UploadRepository;
import com.angio.angiobackend.uploads.type.UploadType;
import com.angio.angiobackend.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Service
public class UploadService {

    private final AngioBackendProperties props;
    private final UploadRepository uploadRepository;
    private final UploadMapper uploadMapper;
    private final UriComponents uploadsUriBuilder;

    public UploadDto uploadImage(MultipartFile file) throws IOException {

        log.info("uploadImage() - start");
        log.info("uploadImage() - save image to file system");
        String savedFilename = FileUtil.saveFile(file, props.getImageUploadExtensions(),
                props.getUploadDirectory());

        String savedFileUri = uploadsUriBuilder.expand(savedFilename).toString();

        log.info("uploadImage() - save image data to database");
        UploadEntity savedImage = uploadRepository.save(new UploadEntity(null, UploadType.IMAGE, savedFilename));

        log.info("uploadImage() - map image data");
        UploadDto savedUploadDto = uploadMapper.map(savedImage, UploadDto.class);
        savedUploadDto.setUri(savedFileUri);

        log.info("uploadImage() - result: {}", savedUploadDto);
        log.info("uploadImage() - end");
        return savedUploadDto;
    }

    public UploadDto uploadDocument(MultipartFile file) throws IOException {

        log.info("uploadDocument() - start");
        log.info("uploadDocument() - save document to file system");
        String savedFilename = FileUtil.saveFile(file, props.getDocumentUploadExtensions(),
                props.getUploadDirectory());

        String savedFileUri = uploadsUriBuilder.expand(savedFilename).toString();

        log.info("uploadDocument() - save document data to database");
        UploadEntity savedImage = uploadRepository.save(new UploadEntity(null, UploadType.DOCUMENT, savedFilename));

        log.info("uploadDocument() - map document data");
        UploadDto savedUploadDto = uploadMapper.map(savedImage, UploadDto.class);
        savedUploadDto.setUri(savedFileUri);

        log.info("uploadDocument() - result: {}", savedUploadDto);
        log.info("uploadDocument() - end");
        return savedUploadDto;
    }
}
