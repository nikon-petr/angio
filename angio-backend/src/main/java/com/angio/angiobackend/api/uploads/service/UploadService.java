package com.angio.angiobackend.api.uploads.service;

import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {

    StaticFileDto uploadImage(MultipartFile file) throws IOException;

    StaticFileDto uploadVideo(MultipartFile file) throws IOException;

    StaticFileDto uploadDocument(MultipartFile file) throws IOException;

    int purgeUnusedImages();
}
