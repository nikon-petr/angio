package com.angio.angiobackend.api.notification.repository;

import com.angio.angiobackend.api.notification.entity.PushNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PushNotificationRepository extends JpaRepository<PushNotification, UUID> {

    @Query("select p from PushNotification p where p.user.id = :id order by p.date desc")
    List<PushNotification> findAllByUser(@Param("id") UUID id);
}
