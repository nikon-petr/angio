export interface UserState {
    fetching: boolean;
    info: UserInfo;
    auth: UserAuth;
    settings: UserSettings;
}

export interface UserInfo {
    id: string | undefined;
    email: string | undefined;
    firstname: string | undefined;
    lastname: string | undefined;
    patronymic: string | undefined;
    permissions: UserPermission[] | [];
}

export interface UserAuth {
    accessToken: string | undefined;
    refreshToken: string | undefined;
}

export interface UserSettings {
    darkThemeEnabled: boolean;
    locale: string;
}

export enum UserPermission {
    ANALYSE_VIEW = 'ANALYSE_VIEW',
    ANALYSE_CREATE = 'ANALYSE_CREATE',
    ANALYSE_EDIT = 'ANALYSE_EDIT',
    ANALYSE_EXECUTE_ACTION = 'ANALYSE_EXECUTE_ACTION',
    ANALYSE_REMOVE = 'ANALYSE_REMOVE',
    ANALYSE_PURGE_DELETED = 'ANALYSE_PURGE_DELETED',

    PATIENT_VIEW = 'PATIENT_VIEW',
    PATIENT_CREATE = 'PATIENT_CREATE',
    PATIENT_EDIT = 'PATIENT_EDIT',
    PATIENT_REMOVE = 'PATIENT_REMOVE',

    DOCUMENT_UPLOAD = 'DOCUMENT_UPLOAD',
    IMAGE_UPLOAD = 'IMAGE_UPLOAD',
    IMAGE_UPLOAD_PURGE_UNUSED = 'IMAGE_UPLOAD_PURGE_UNUSED',

    USER_VIEW = 'USER_VIEW',
    USER_CREATE = 'USER_CREATE',
    USER_EDIT = 'USER_EDIT',
    USER_REMOVE = 'USER_REMOVE',

    TOKEN_VIEW = 'TOKEN_VIEW',
    TOKEN_REVOKE = 'TOKEN_REVOKE',
    TOKEN_REMOVE = 'TOKEN_REMOVE',

    PUSH_NOTIFICATION_RECEIVE = 'PUSH_NOTIFICATION_RECEIVE',
    PUSH_NOTIFICATION_SEND = 'PUSH_NOTIFICATION_SEND',
}
