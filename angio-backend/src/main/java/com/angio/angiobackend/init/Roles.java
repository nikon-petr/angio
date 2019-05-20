package com.angio.angiobackend.init;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  Roles {

    DOCTOR(new Permissions[]{
            Permissions.ANALYSE_CREATE,
            Permissions.ANALYSE_VIEW,
            Permissions.ANALYSE_EDIT,
            Permissions.ANALYSE_EXECUTE_ACTION,
            Permissions.ANALYSE_REMOVE,

            Permissions.PATIENT_CREATE,
            Permissions.PATIENT_VIEW,
            Permissions.PATIENT_EDIT,
            Permissions.PATIENT_REMOVE,

            Permissions.IMAGE_UPLOAD,

            Permissions.TOKEN_REVOKE,

            Permissions.PUSH_NOTIFICATION_RECEIVE
    }, "Врач диагност"),

    ADMIN(new Permissions[]{
            Permissions.ANALYSE_PURGE_DELETED,

            Permissions.USER_CREATE,
            Permissions.USER_VIEW,
            Permissions.USER_EDIT,
            Permissions.USER_REMOVE,

            Permissions.ORGANIZATION_CREATE,
            Permissions.ORGANIZATION_VIEW,
            Permissions.ORGANIZATION_EDIT,
            Permissions.ORGANIZATION_REMOVE,

            Permissions.ACTUATOR_VIEW,

            Permissions.TOKEN_VIEW,
            Permissions.TOKEN_REVOKE,
            Permissions.TOKEN_REMOVE,

            Permissions.IMAGE_UPLOAD_PURGE_UNUSED,

            Permissions.PUSH_NOTIFICATION_RECEIVE,
            Permissions.PUSH_NOTIFICATION_SEND
    }, "Администратор системы"),

    ROOT(Permissions.values(), "Супер пользователь системы");

    private Permissions[] permissions;
    private String description;
}
