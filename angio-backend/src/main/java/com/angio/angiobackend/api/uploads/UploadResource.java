package com.angio.angiobackend.api.uploads;

import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import com.angio.angiobackend.api.uploads.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "Uploads", description = "Angio uploads REST API")
@RequestMapping(path = "/api/v2/upload")
public class UploadResource {

    private final UploadService uploadService;

    @ApiOperation(value = "Resource to upload image")
    @PostMapping("/image")
    public StaticFileDto uploadImage(MultipartFile multipartFile) throws IOException {
        return uploadService.uploadImage(multipartFile);
    }

    @ApiOperation(value = "Resource to upload video")
    @PostMapping("/video")
    public StaticFileDto uploadVideo(MultipartFile multipartFile) throws IOException {
        return uploadService.uploadVideo(multipartFile);
    }

    @ApiOperation(value = "Resource to upload document")
    @PostMapping("/document")
    public StaticFileDto uploadDocument(MultipartFile multipartFile) throws IOException {
        return uploadService.uploadDocument(multipartFile);
    }

    @ApiOperation(value = "Resource to purge unused images")
    @DeleteMapping("/image")
    @PreAuthorize("hasAuthority('IMAGE_UPLOAD_PURGE_UNUSED')")
    public Integer purgeUnusedImages() {
        return uploadService.purgeUnusedImages();
    }
}
