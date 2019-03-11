package com.angio.angiobackend.api.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationDto {

    private Date date;

    private String title;

    private String body;

    private AbstractSubject subject;

    private Boolean read;
}
