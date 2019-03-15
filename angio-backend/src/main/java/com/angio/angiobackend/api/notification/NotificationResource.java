package com.angio.angiobackend.api.notification;

import com.angio.angiobackend.api.common.dto.Response;
import com.angio.angiobackend.api.notification.dto.NewNotificationDto;
import com.angio.angiobackend.api.notification.dto.PushNotificationDto;
import com.angio.angiobackend.api.notification.service.impl.PushNotificationService;
import com.angio.angiobackend.api.user.service.UserService;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "Notifications", description = "Notifications REST API")
@RequestMapping(path = "/api/v2/notification")
public class NotificationResource {

    private final UserService userService;
    private final PushNotificationService pushNotificationService;

    @ApiOperation("Get waiting for notifications")
    @GetMapping("/push")
    @PreAuthorize("hasAuthority('PUSH_NOTIFICATION_RECEIVE')")
    public DeferredResult<PushNotificationDto> getNotifications() {
        DeferredResult<PushNotificationDto> response = new DeferredResult<>();
        pushNotificationService.addResponse(userService.getUserIdFromContext(), response);
        return response;
    }

    @ApiOperation("Send push notification message to single user or group of users. " +
            "If no user id is specified notification will be sent to all users.")
    @PostMapping("/push")
    @PreAuthorize("hasAuthority('PUSH_NOTIFICATION_SEND')")
    public Response sendNotification(@RequestParam(required = false) List<UUID> userIds,
                                     @RequestBody @Validated NewNotificationDto newNotification) {

        if (userIds == null) {
            pushNotificationService.notifyAllUsers(newNotification);
        } else {
            pushNotificationService.notifyUsers(userIds, newNotification);
        }

        return Response.success(null);
    }
}
