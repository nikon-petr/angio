package com.angio.angiobackend.api.notification.repository;

import com.angio.angiobackend.api.notification.entity.PushNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<PushNotification, UUID> {
}
