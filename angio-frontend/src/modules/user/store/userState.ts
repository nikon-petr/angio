import FullName from '@/modules/common/models/fullName';

export interface UserState {
    fetching: boolean;
    info: UserInfo;
    auth: UserAuth;
    settings: UserSettings;
}

export interface UserInfo {
    id?: string;
    email?: string;
    fullName: FullName;
    organizationName?: string;
    permissions: UserPermission[] | [];
}

export interface UserAuth {
    accessToken?: string;
    refreshToken?: string;
}

export interface UserSettings {
    darkThemeEnabled: boolean;
    locale: Locale;
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

    ORGANIZATION_VIEW = 'ORGANIZATION_VIEW',
    ORGANIZATION_CREATE = 'ORGANIZATION_CREATE',
    ORGANIZATION_EDIT = 'ORGANIZATION_EDIT',
    ORGANIZATION_REMOVE = 'ORGANIZATION_REMOVE',

    TOKEN_VIEW = 'TOKEN_VIEW',
    TOKEN_REVOKE = 'TOKEN_REVOKE',
    TOKEN_REMOVE = 'TOKEN_REMOVE',

    PUSH_NOTIFICATION_RECEIVE = 'PUSH_NOTIFICATION_RECEIVE',
    PUSH_NOTIFICATION_SEND = 'PUSH_NOTIFICATION_SEND',
}

export enum Locale {
    EN = 'en',
    RU = 'ru',
}
