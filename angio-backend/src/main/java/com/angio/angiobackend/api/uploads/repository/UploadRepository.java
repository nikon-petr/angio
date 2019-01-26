package com.angio.angiobackend.api.uploads.repository;

import com.angio.angiobackend.api.uploads.entity.StaticFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadRepository extends JpaRepository<StaticFileEntity, Long> {
}
