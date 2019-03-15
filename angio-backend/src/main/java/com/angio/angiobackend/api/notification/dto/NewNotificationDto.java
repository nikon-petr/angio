package com.angio.angiobackend.api.notification.dto;

import com.angio.angiobackend.api.notification.type.NotificationType;
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

    private Date date;

    @NotNull
    private NotificationType type;

    @NotNull
    private String templateName;

    @NotNull
    private String title;

    @NotNull
    private SubjectDto subject;

    @NotNull
    private Object dataModel;
}
