package com.angio.angiobackend.api.notification.dto;

import java.util.Date;

public interface AbstractNotification<ID> {

    ID getId();

    Date getDate();

    String getPayload();

    Boolean getRead();

    String getTag();
}
