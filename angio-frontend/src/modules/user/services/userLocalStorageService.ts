import root, {Logger} from 'loglevel';
import ls from 'local-storage';
import {UserAuth, UserInfo, UserPermission, UserSettings} from '@/modules/user/store/userState';

const log: Logger = root.getLogger('store/helpers/user');

export class UserLocalStorageService {
    public static purgeUser() {
        log.debug('clear local storage');
        ls.remove('accessToken');
        ls.remove('refreshToken');
        ls.remove('id');
        ls.remove('email');
        ls.remove('firstname');
        ls.remove('lastname');
        ls.remove('patronymic');
        ls.remove('permissions');
    }

    public static persistAccessToken(accessToken: string) {
        log.debug('save access token to local storage');
        ls.set('accessToken', accessToken);
    }

    public static persistUserAuth(userAuth: UserAuth) {
        log.debug('save access and refresh token to local storage');
        ls.set('accessToken', userAuth.accessToken);
        ls.set('refreshToken', userAuth.refreshToken);
    }

    public static persistUserInfo(userInfo: UserInfo) {
        log.debug('save user data to local storage');
        ls.set('id', userInfo.id);
        ls.set('email', userInfo.email);
        ls.set('firstname', userInfo.firstname);
        ls.set('lastname', userInfo.lastname);
        ls.set('patronymic', userInfo.patronymic);
        ls.set('permissions', userInfo.permissions);
        ls.set('firstname', userInfo.firstname);
    }

    public static persistUserPermissions(permissions: UserPermission[]) {
        ls.set('permissions', permissions);
    }

    public static persistUserSetting(settings: UserSettings) {
        log.debug('save user settings to local storage');
        ls.set('settings.darkThemeEnabled', settings.darkThemeEnabled);
        ls.set('settings.locale', settings.locale);
    }
}

