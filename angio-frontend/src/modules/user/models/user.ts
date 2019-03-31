import {UserAuth, UserInfo, UserPermission, UserSettings} from '@/modules/user/store/userState';
import FullName from '@/modules/common/models/fullName';

export interface UserInfoModel extends UserInfo {
    id: string;
    email: string;
    fullName: FullName;
    permissions: UserPermission[] | [];
    settings: UserSettingsModel;
}

export interface UserAuthModel extends UserAuth {
    access_token: string;
    refresh_token: string;
}

export interface UserSettingsModel extends UserSettings {
    darkThemeEnabled: boolean;
    locale: string;
}

export interface UserCredentialsModel {
    username: string;
    password: string;
}

export interface RefreshTokenModel {
    access_token: string;
}
