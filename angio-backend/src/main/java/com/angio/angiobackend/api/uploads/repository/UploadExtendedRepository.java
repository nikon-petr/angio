package com.angio.angiobackend.api.uploads.repository;

import com.angio.angiobackend.api.uploads.entity.StaticFile;

import java.util.List;

public interface UploadExtendedRepository {

    List<StaticFile> getUnlinkedImages();
}
