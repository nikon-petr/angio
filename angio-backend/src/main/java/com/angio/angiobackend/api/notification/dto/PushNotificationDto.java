package com.angio.angiobackend.api.notification.dto;

import com.angio.angiobackend.api.notification.type.NotificationType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationDto {

    @ApiModelProperty(name = "Notification id")
    private UUID id;

    @ApiModelProperty(name = "Notification type")
    private Date date;

    @ApiModelProperty(name = "Notification type")
    private NotificationType type;

    @ApiModelProperty(name = "Notification body")
    private String body;

    @ApiModelProperty(name = "Notification subject")
    private AbstractSubject subject;

    @ApiModelProperty(name = "Notification read")
    private Boolean read;
}
