package com.angio.angiobackend.api.uploads.service;

import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {

    @Transactional
    StaticFileDto uploadImage(@NonNull MultipartFile file) throws IOException;

    @Transactional
    StaticFileDto uploadDocument(@NonNull MultipartFile file) throws IOException;

    @Transactional
    int purgeUnusedImages();
}
