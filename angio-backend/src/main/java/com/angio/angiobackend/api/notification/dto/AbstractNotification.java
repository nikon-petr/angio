package com.angio.angiobackend.api.notification.dto;

import com.angio.angiobackend.api.notification.type.NotificationType;

import java.util.Date;

public interface AbstractNotification {

    Date getDate();

    NotificationType getType();

    String getTemplateName();

    AbstractSubject getSubject();

    Object getDataModel();
}
