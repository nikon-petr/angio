package com.angio.angiobackend.api.notification.dto;

import com.angio.angiobackend.api.notification.type.NotificationType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class NewNotificationDto implements AbstractNotification {

    @ApiModelProperty(name = "Notification type", readOnly = true)
    private Date date;

    @NotNull
    @ApiModelProperty(name = "Notification type")
    private NotificationType type;

    @NotNull
    @ApiModelProperty(name = "Notification template file name")
    private String templateName;

    @NotNull
    @ApiModelProperty(name = "Notification subject")
    private SubjectDto subject;

    @NotNull
    @ApiModelProperty(name = "Notification data model for applying to template")
    private Object dataModel;
}
