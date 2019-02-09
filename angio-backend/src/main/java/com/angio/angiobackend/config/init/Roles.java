package com.angio.angiobackend.config.init;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  Roles {
    ADMIN(new Permissions[]{
            Permissions.USER_CREATE,
            Permissions.USER_VIEW,
            Permissions.USER_EDIT,
            Permissions.USER_REMOVE,

            Permissions.TOKEN_VIEW,
            Permissions.TOKEN_REVOKE,
            Permissions.TOKEN_REMOVE
    }),

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
    });

    private Permissions[] permissions;
}