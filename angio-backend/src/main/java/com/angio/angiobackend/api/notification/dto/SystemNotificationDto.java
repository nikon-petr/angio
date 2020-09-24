package com.angio.angiobackend.api.notification.dto;

import com.angio.angiobackend.api.common.dto.AbstractEmailDto;
import com.angio.angiobackend.api.notification.type.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SystemNotificationDto extends AbstractEmailDto {
    private Date date;
    private NotificationType type;
    private String text;
}
