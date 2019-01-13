package com.angio.angiobackend.uploads.repository;

import com.angio.angiobackend.uploads.entity.UploadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadRepository extends JpaRepository<UploadEntity, Long> {
}
