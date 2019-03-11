package com.angio.angiobackend.api.notification.dto;

import java.util.Date;

public interface AbstractNotification {

    Date getDate();

    String getTemplateName();

    String getTitle();

    AbstractSubject getSubject();

    Object getDataModel();
}
