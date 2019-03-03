package com.angio.angiobackend.api.notification.service;

import lombok.NonNull;

import java.util.Collection;

/**
 * Abstract notification service.
 *
 * @param <ID> id type of user entity
 */
public interface NotificationService<ID> {

    /**
     * Send notification to user with given id.
     *
     * @param id user id
     * @param payload notification payload
     * @param tag notification tag, e.g. name of updated domain entity
     */
    void notifyUser(@NonNull ID id, @NonNull String payload, @NonNull String tag);

    /**
     * Send notification to users with given ids.
     *
     * @param ids users ids
     * @param payload notification payload
     * @param tag notification tag, e.g. name of updated domain entity
     */
    void notifyUsers(@NonNull Collection<ID> ids, @NonNull String payload, @NonNull String tag);

    /**
     * Send notification to all users.
     *
     * @param payload notification payload
     * @param tag notification tag, e.g. name of updated domain entity
     */
    void notifyAllUsers(@NonNull String payload, @NonNull String tag);
}
