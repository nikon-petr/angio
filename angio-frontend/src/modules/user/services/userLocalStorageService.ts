import root, {Logger} from 'loglevel';
import ls from 'local-storage';
import {UserAuth, UserInfo, UserPermission, UserSettings} from '@/modules/user/store/userState';

export class UserLocalStorageService {
    private static log: Logger = root.getLogger('UserLocalStorageService');

    public static purgeUser() {
        UserLocalStorageService.log.debug('clear local storage');
        ls.remove('accessToken');
        ls.remove('refreshToken');
        ls.remove('id');
        ls.remove('email');
        ls.remove('firstname');
        ls.remove('lastname');
        ls.remove('patronymic');
        ls.remove('organizationName');
        ls.remove('permissions');
    }

    public static persistAccessToken(accessToken: string) {
        UserLocalStorageService.log.debug('save access token to local storage');
        ls.set('accessToken', accessToken);
    }

    public static persistUserAuth(userAuth: UserAuth) {
        UserLocalStorageService.log.debug('save access and refresh token to local storage');
        ls.set('accessToken', userAuth.accessToken);
        ls.set('refreshToken', userAuth.refreshToken);
    }

    public static persistUserInfo(userInfo: UserInfo) {
        UserLocalStorageService.log.debug('save user data to local storage');
        ls.set('id', userInfo.id);
        ls.set('email', userInfo.email);
        ls.set('firstname', userInfo.fullName.firstname);
        ls.set('lastname', userInfo.fullName.lastname);
        ls.set('patronymic', userInfo.fullName.patronymic || null);
        ls.set('organizationName', userInfo.organizationName || null);
        ls.set('permissions', userInfo.permissions);
    }

    public static persistUserPermissions(permissions: UserPermission[]) {
        UserLocalStorageService.log.debug('save user permissions to local storage');
        ls.set('permissions', permissions);
    }

    public static persistUserSetting(settings: UserSettings) {
        UserLocalStorageService.log.debug('save user settings to local storage');
        ls.set('darkThemeEnabled', settings.darkThemeEnabled);
        ls.set('locale', settings.locale);
    }
}

