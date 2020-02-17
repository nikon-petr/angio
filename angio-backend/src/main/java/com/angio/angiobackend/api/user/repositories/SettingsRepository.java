package com.angio.angiobackend.api.user.repositories;

import com.angio.angiobackend.api.user.entities.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SettingsRepository extends JpaRepository<Settings, UUID> {
}
