package com.angio.angiobackend.api.uploads.service.impl;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import com.angio.angiobackend.api.uploads.entity.StaticFile;
import com.angio.angiobackend.api.uploads.mapper.UploadMapper;
import com.angio.angiobackend.api.uploads.repository.UploadRepository;
import com.angio.angiobackend.api.uploads.service.UploadService;
import com.angio.angiobackend.api.uploads.type.FileType;
import com.angio.angiobackend.util.FileUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {

    private final AngioBackendProperties props;
    private final UploadRepository uploadRepository;
    private final UploadMapper uploadMapper;
    private final DynamicLocaleMessageSourceAccessor msa;

    /**
     * Upload image if it matches extension defined in angio.app.image-upload-extensions property
     * else throw {@link IllegalArgumentException}.
     *
     * @param file image file
     * @return saved file
     * @throws IOException
     */
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('IMAGE_UPLOAD')")
    public StaticFileDto uploadImage(@NonNull MultipartFile file) throws IOException {

        log.debug("uploadImage() - start");
        log.debug("uploadImage() - save image to file system");
        validateFile(file);
        String savedFilename = FileUtils.saveFile(file, props.getUploadDirectory());

        log.debug("uploadImage() - save image data to database");
        StaticFile savedImage = uploadRepository.save(new StaticFile(null, FileType.IMAGE, savedFilename));

        log.debug("uploadImage() - map image data");
        StaticFileDto savedStaticFileDto = uploadMapper.toExternalDto(savedImage);

        log.debug("uploadImage() - result: {}", savedStaticFileDto);
        log.debug("uploadImage() - end");
        return savedStaticFileDto;
    }

    /**
     * Upload document if it matches extension defined in angio.app.document-upload-extensions property
     * else throw {@link IllegalArgumentException}.
     *
     * @param file file
     * @return saved file
     * @throws IOException
     */
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('DOCUMENT_UPLOAD')")
    public StaticFileDto uploadDocument(@NonNull MultipartFile file) throws IOException {

        log.debug("uploadDocument() - start");
        log.debug("uploadDocument() - save document to file system");
        validateFile(file);
        String savedFilename = FileUtils.saveFile(file, props.getUploadDirectory());

        log.debug("uploadDocument() - save document data to database");
        StaticFile savedImage = uploadRepository.save(new StaticFile(null, FileType.DOCUMENT, savedFilename));

        log.debug("uploadDocument() - map document data");
        StaticFileDto savedStaticFileDto = uploadMapper.toExternalDto(savedImage);

        log.debug("uploadDocument() - result: {}", savedStaticFileDto);
        log.debug("uploadDocument() - end");
        return savedStaticFileDto;
    }

    /**
     * Purge unused images.
     *
     * @return count of deleted images
     */
    @Override
    @Transactional
    public int purgeUnusedImages() {
        log.debug("purgeUnusedImages() - start");
        List<StaticFile> unlinkedImages = uploadRepository.getUnlinkedImages();

        log.debug("purgeUnusedImages() - ids to delete: {}", unlinkedImages.stream().mapToLong(StaticFile::getId).toArray());
        if (unlinkedImages.size() > 0) {
            for (StaticFile image : unlinkedImages) {
                FileUtils.deleteFile(image.getFilename(), props.getUploadDirectory());
            }

            uploadRepository.deleteInBatch(unlinkedImages);
        }

        log.debug("purgeUnusedImages() - end");
        return unlinkedImages.size();
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
