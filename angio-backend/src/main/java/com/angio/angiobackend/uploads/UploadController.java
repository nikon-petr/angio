package com.angio.angiobackend.uploads;

import com.angio.angiobackend.uploads.dto.UploadDto;
import com.angio.angiobackend.uploads.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(description = "Upload controller")
@RequestMapping(path = "/api/v2/upload")
public class UploadController {

    private final UploadService uploadService;

    @ApiOperation(value = "Resource to upload image", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Client error"),
            @ApiResponse(code = 500, message = "Server error")})
    @PostMapping(path = "/image")
    public UploadDto uploadImage(MultipartFile multipartFile) throws IOException {
        return uploadService.uploadImage(multipartFile);
    }

    @ApiOperation(value = "Resource to upload document", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Client error"),
            @ApiResponse(code = 500, message = "Server error")})
    @PostMapping(path = "/document")
    public UploadDto uploadDocument(MultipartFile multipartFile) throws IOException {
        return uploadService.uploadDocument(multipartFile);
    }
}
