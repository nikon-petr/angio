package com.angio.angiobackend.api.notification.mapper;

import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import com.angio.angiobackend.api.notification.dto.PushNotificationDto;
import com.angio.angiobackend.api.notification.entity.PushNotification;
import org.mapstruct.Mapper;

@Mapper(uses = {SubjectMapper.class})
public interface PushNotificationMapper extends AbstractMapper<PushNotification, PushNotificationDto> {
}
